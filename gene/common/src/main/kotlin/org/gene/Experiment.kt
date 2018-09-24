package org.gene

import com.bftcom.ice.common.maps.DataMapF
import org.gene.view.*

interface Experiment2Service {

    fun newExperiment(D: Int, N: Int)
    fun initLineInstance()
    fun getGridStateForView():DataMapF<GridState>
}

class Experiment2(val D: Int, N: Int) {

    val grid = Grid2(D)
    val chain = DasWave(grid, N)

    private val D1 = D - 1

    fun initLineConformation(horisontal: Boolean, startPosition: Point2) {
        chain.initLineInstance(horisontal, startPosition)
    }

    fun reset() {
        grid.reset()
        chain.reset()
    }

    fun getGridStateForView(atop: Point2 = Point2(0, 0), abottom: Point2 = Point2(D1, D1)): DataMapF<GridState> {
        return GridState { state ->
            state[top] = PointState.fromPoint(atop)
            state[bottom] = PointState.fromPoint(abottom)
            grid.doWithEveryCellInBox(

                    {
                        val cell = this
                        if (cell.charge > Charges.FREE) {
                            state[cells].add(
                                    CellState { cs ->
                                        cs[x] = cell.point.x
                                        cs[y] = cell.point.y
                                        cs[charge] = cell.charge
                                    })
                        }
                    }, atop, abottom)
        }
    }

}

