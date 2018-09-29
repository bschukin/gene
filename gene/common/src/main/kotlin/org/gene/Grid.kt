package org.gene

/**
 * Периодическая решетка [0,0]x[D-1, D-1]
 */
class Grid2(
        //размер решетки
        val D: Int) {

    val D1 = D-1

    //матрица
    val XY: Array<Array<Cell2>> = Array(D,
            {
                val x = it
                Array(D, {
                    val y = it
                    Cell2(Point2(x, y))
                })
            })

    val top = Point2(0, 0)
    val bottom = Point2(D - 1, D - 1)

    fun reset() {
        XY.forEach { Y ->
            Y.forEach { cell2 ->
                cell2.reset()
            }
        }
    }

    fun getChargeXY(lat: Point2): Byte {
        return XY[D1 and lat.x][D1 and lat.y].charge
    }

    fun freeXY(lat: Point2) {
        chargeXY(lat, Charges.FREE)
    }

    fun chargeXY(lat: Point2, charger: Byte) {
        XY[D1 and lat.x][D1 and lat.y ].charge(charger)
    }

    fun chargeXY(pNode: Node2) {
        val x = D1 and pNode.position!!.x
        val y = D1 and pNode.position!!.y
        XY[x][y].charge(pNode)
    }

    fun nodePositionOnGrid(pNode: Node2):Point2 {
        val x = D1 and pNode.position!!.x
        val y = D1 and pNode.position!!.y
        return Point2(x, y)
    }

    fun getRandomPointInItsBox(): Point2 {
        return getRandomPointInBox(top, bottom)
    }

    internal fun getRandomFreePointInItsBox(): Point2 {
        var v: Point2? = null
        while (true) {
            v = getRandomPointInItsBox()
            if (getChargeXY(v) == Charges.FREE) return v
        }
    }

    fun doWithEveryCellInBox(cellOperator: Cell2.() -> Unit,
                             top: Point2 = Point2(0, 0),
                             bottom: Point2 = Point2(D - 1, D - 1)) {
        for (i in top.x..bottom.x)
            for (j in top.y..bottom.y) {
                cellOperator.invoke(XY[i][j])
            }
    }

    class Cell2(val point: Point2) {

        var charge = Charges.FREE
        var energy = 0.0
        var node: Node2? = null

        fun reset() {
            charge = Charges.FREE
            energy = 0.0
            node = null
        }

        fun charge(value: Byte) {
            charge = value
            node = null
        }

        fun charge(node: Node2) {
            charge(node.type.value)
            this.node = node
        }

        override fun toString(): String {
            return "(point=$point, charge=$charge)"
        }


    }
}