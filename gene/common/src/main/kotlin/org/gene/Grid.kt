package org.gene

/**
 * Непериодическа решетка [0,0]x[D-1, D-1]
 */
class Grid2(
        //размер решетки
        val D: Int = 0) {
    private val D1 = D - 1

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
        return XY[lat.x][lat.y].charge
    }

    fun freeXY(lat: Point2) {
        XY[lat.x][lat.y].charge(Charges.FREE)
    }

    fun chargeXY(lat: Point2, charger: Byte) {
        XY[lat.x][lat.y].charge(charger)
    }

    fun chargeXY(pNode: Node2) {
        XY[pNode.position!!.x][pNode.position!!.y].charge(pNode)
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
        }

        fun charge(node: Node2) {
            charge(node.type.value)
            this.node = node
        }
    }
}