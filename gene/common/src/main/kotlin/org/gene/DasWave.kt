package org.gene

import com.bftcom.ice.common.general.throwImpossible

class Node2(var type: NodeType, internal var index: Int) {

    var position: Point2? = null

    override fun toString(): String {
        return "Node{" +
                "type=" + type +
                ", position=" + position +
                ", index=" + index +
                '}'.toString()
    }
}


class DasWave(
        val XY: Grid2,
        //количество реальных и виртуальных  звеньев
        val N: Int) {

    private var NU: Int = 0 //реальный размер массива (с учетом виртуальных звеньев)
    private var VU: Int = 0 //количество виртуальынх звеньев в текущий момент

    //массив узлов. Внимание это не упорядоченный массив узлов ( по направлению pFirst-PNext).
    var pNodes: MutableList<Node2>


    fun reset() {
        pNodes.clear()
        initNodes()
    }

    private fun initNodes() {
        for (i in 0 until NU) {
            //реальные звенья
            val type = if (i % 2 == 0) NodeType.A_UNIT else NodeType.A_VU
            pNodes.add(Node2(type, i))
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

    fun printCoorinates() {

        for (i in 0 until NU) {
            println(pNodes[i])
        }
    }

    init {
        NU = N * 2 - 1
        VU = 0
        pNodes = ArrayList(NU)
        initNodes()
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
            FREE->return "00"
        }
        throwImpossible()
    }
}