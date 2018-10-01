package org.gene

import com.bftcom.ice.common.general.makeSure
import com.bftcom.ice.common.general.throwImpossible
import org.gene.Charges.FREE
import org.gene.Urand.GetRandomMoveVector8
import org.gene.moving.randomMoveOperator

class DasWave(
        val XY: Grid2,
        //количество реальных и виртуальных  звеньев
        val N: Int) {


    private var NU: Int = N * 2 - 1 //реальный размер массива (с учетом виртуальных звеньев)
    private var VU: Int = 0 //количество виртуальынх звеньев в текущий момент
    //массив узлов. Внимание это не упорядоченный массив узлов ( по направлению pFirst-PNext).
    var pNodes: MutableList<Node2> = ArrayList(NU)

    init {
        initNodes()
    }

    fun reset() {
        pNodes.clear()
        initNodes()
    }

    private fun initNodes() {
        for (i in 0 until NU) {
            //реальные звенья
            val type = if (i % 2 == 0) NodeType.A_UNIT else NodeType.A_VU
            pNodes.add(Node2(type, i, this))
        }
    }

    fun initLineInstance(direction: Boolean, startPosition: Point2) {
        val fromPrev = if (direction) v10 else v01

        var position: Point2 = startPosition
        for (i in 0 until N) {
            val j = i * 2
            pNodes[j].position = position// +  fromPrev
            position += fromPrev

            XY.chargeXY(pNodes[j])
        }
    }

    fun tryRandomMove(): Boolean {
        return randomMoveOperator(pNodes[getRandomInt(N) * 2])
    }


    class SelectContext(var canSelect: Boolean = false,
                        var vPrev: Point2? = null,
                        var vNext: Point2? = null,
                        var v2Prev: Point2? = null)

    fun selectPrevVirtPos(pNode: Node2, newPos: Point2, distPrev: Int, distNext: Int, ctx: SelectContext) {
        if (pNode.isFirst()) {
            return selectNextVirtPos(pNode, newPos, distNext, ctx)
        }

        //расстояние == 2
        if (distPrev == 3) {
            ctx.vPrev = (newPos + pNode.prevReal()!!.position!!) / 2;
            if (XY.getChargeXY(ctx.vPrev!!) != FREE) return
        }
        //расстояние == sqrt(2)
        if (distPrev == 2) {
            val v1 = Point2(newPos.x, pNode.prevReal()!!.position!!.y)
            val v2 = Point2(pNode.prevReal()!!.position!!.x, newPos.y)
            val K = if (XY.getChargeXY(v1) == FREE) 1 else 0 + if (XY.getChargeXY(v2) == FREE) 2 else 0
            when (K) {
                0 -> return
                1 -> ctx.vPrev = v1
                2 -> ctx.vPrev = v2
                3 -> {
                    val R = getRandomInt(2);
                    ctx.vPrev = if (R == 0) v1 else v2
                    ctx.v2Prev = if (R == 0) v2 else v1
                }
            }
        }

        //начинаем анализ для виртуала Next
        selectNextVirtPos(pNode, newPos, distNext, ctx)
    }

    fun selectNextVirtPos(pNode: Node2, newPos: Point2, distNext: Int, ctx: SelectContext) {

        if (distNext == 1 || pNode.isLast()) {
            ctx.canSelect = true
            return
        }

        if (distNext == 3) {
            ctx.vNext = (newPos + pNode.nextReal()!!.position!!) / 2
            if (XY.getChargeXY(ctx.vNext!!) != FREE) return
            if (ctx.vNext == ctx.vPrev) {
                if (ctx.v2Prev == null) return
                else ctx.vPrev = ctx.v2Prev
            }
            ctx.canSelect = true
            return
        }

        if (distNext == 2) {
            var v1 = Point2(newPos.x, pNode.nextReal()!!.position!!.y)
            var v2 = Point2(pNode.nextReal()!!.position!!.x, newPos.y)
            val K = (if (XY.getChargeXY(v1) == FREE) 1 else 0) + (if (XY.getChargeXY(v2) == FREE) 2 else 0)
            when (K) {
                0 -> return
                1 -> {
                    ctx.vNext = v1
                    if (ctx.vNext == ctx.vPrev) {
                        if (ctx.v2Prev == null) return
                        else ctx.vPrev = ctx.v2Prev;
                    }
                    ctx.canSelect = true
                    return
                }
                2 -> {
                    ctx.vNext = v2
                    if (ctx.vNext == ctx.vPrev) {
                        if (ctx.v2Prev == null) return
                        else ctx.vPrev = ctx.v2Prev
                    }
                    ctx.canSelect = true
                    return
                }
                3 -> {
                    if (getRandomInt(2) == 0) {
                        val temp = v1;
                        v1 = v2
                        v2 = temp
                    }
                    ctx.vNext = (if (v1 == ctx.vPrev) v2 else v1)
                    ctx.canSelect = true
                    return
                }
            }
        }

    }

    internal fun freeInnerAndItsVirtuals(pBufNode: Node2) {
        XY.freeXY(pBufNode.position!!)

        pBufNode.nextVirt()?.let {
            it.position?.let {
                XY.freeXY(it)
            }
        }
        pBufNode.prevVirt()?.let {
            it.position?.let {
                XY.freeXY(it)
            }
        }
    }

    internal fun chargeInnerAndItsVirtuals(pBufNode: Node2) {
        XY.chargeXY(pBufNode)

        pBufNode.nextVirt()?.let { n ->
            n.position?.let {
                XY.chargeXY(n)
            }
        }
        pBufNode.prevVirt()?.let { n ->
            n.position?.let {
                XY.chargeXY(n)
            }
        }
    }

    fun printCoordinates() {

        for (i in 0 until NU) {
            println(pNodes[i])
        }
    }


}

enum class NodeType constructor(internal var value: Byte) {
    A_UNIT(Charges.A_UNIT),
    A_VU(Charges.A_VU),
    A_KINK(Charges.A_KINK)
}


object Charges {

    val UNKNOWN: Byte = -1

    val FREE: Byte = 0
    val CUMBER: Byte = 1

    val BARRIER: Byte = 2 //
    val A_UNIT: Byte = 10 //звено цепи, тип А
    val A_VU: Byte = 11   //виртуальное звено цепи, тип А
    val A_KINK: Byte = 14   //виртуальное звено цепи, тип А
    val B_UNIT: Byte = 12
    val B_VU: Byte = 13

    fun getChargeName(charge: Byte): String {
        when (charge) {
            A_UNIT -> return "REAL UNIT"
            A_VU -> return "VIRT UNIT"
        }
        throwImpossible()
    }

    fun getChargeLogo(charge: Byte): String {
        when (charge) {
            A_UNIT -> return "R "
            A_VU -> return "V "
            FREE -> return "00"
        }
        throwImpossible()
    }
}

class Node2(var type: NodeType, internal var index: Int, var chain: DasWave) {

    var position: Point2? = null

    override fun toString(): String {
        return "Node{" +
                "type=" + type +
                ", position=" + position +
                ", index=" + index +
                '}'.toString()
    }

    fun isLast(): Boolean = this.index == chain.pNodes.size - 1
    fun isFirst(): Boolean = this.index == 0
    fun isVisible(): Boolean = this.position != null
    fun isReal(): Boolean = this.type == NodeType.A_UNIT
    fun isVirtual(): Boolean = this.type == NodeType.A_VU
    fun next(): Node2? = if (isLast()) null else chain.pNodes[index + 1]
    fun prev(): Node2? = if (isFirst()) null else chain.pNodes[index - 1]

    fun nextVisible(): Node2? {
        val pNext = next() ?: return null
        if (pNext.isVisible())
            return pNext
        return pNext.nextVisible()
    }

    fun prevVisible(): Node2? {
        val pPrev = prev() ?: return null
        if (pPrev.isVisible())
            return pPrev
        return pPrev.prevVisible()
    }

    fun prevVirt(): Node2? {
        makeSure(isReal())
        return if (isFirst()) null else prev()
    }

    fun nextVirt(): Node2? {
        makeSure(isReal())
        return if (isLast()) null else next()
    }

    fun prevReal(): Node2? {
        makeSure(isReal())
        return if (isFirst()) null else prev()!!.prev()
    }

    fun nextReal(): Node2? {
        makeSure(isReal())
        return if (isLast()) null else next()!!.next()
    }
}

val falsePair = Pair<Boolean, Point2?>(false, null)
