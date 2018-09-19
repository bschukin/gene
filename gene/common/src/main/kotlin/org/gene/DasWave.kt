package org.gene

import com.bftcom.ice.common.general.throwImpossible

class Node2(var type: NodeType, internal var index: Int) {

    var position: Point2? = null
    var prev: Node2? = null
    var next: Node2? = null

    override fun toString(): String {
        return "Node{" +
                "type=" + type +
                ", position=" + position +
                ", index=" + index +
                '}'.toString()
    }
}


public class DasWave {

    val XY: Grid2
    internal var N: Int = 0   //всего элементов в цепи
    internal var NU: Int = 0 //реальный размер массива (с учетом виртуальных звеньев)
    internal var VU: Int = 0 //количество виртуальынх звеньев в текущий момент

    //массив узлов. Внимание это не упорядоченный массив узлов ( по направлению pFirst-PNext).
    private var pNodes: MutableList<Node2>


    constructor(SPACE: Grid2, count: Int) {
        this.XY = SPACE
        N = count
        NU = N * 2 - 1 //количество реальныхи и виртуальных  звеньев
        VU = 0
        pNodes = ArrayList(NU)

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
}