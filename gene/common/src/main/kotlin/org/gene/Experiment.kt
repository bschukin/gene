package org.gene

import com.bftcom.ice.common.maps.DataMapF
import org.gene.view.*

interface Experiment2Service {

    fun newExperimentAndState(): DataMapF<GridState>
    fun newExperiment(D: Int, N: Int)
    fun initLineInstance()
    fun getGridStateForView(): DataMapF<GridState>
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
        val res =  GridState { state ->
            state[id] = 100
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
                        val node = cell.node
                        if (node != null) {
                            if (node.prevVisible() != null) {
                                state[links].add(LinkState { ls ->
                                    ls[x1] = node.position!!.x
                                    ls[y1] = node.position!!.y
                                    ls[x2] = node.prevVisible()!!.position!!.x
                                    ls[y2] = node.prevVisible()!!.position!!.y
                                })
                            }
                            if (node.nextVisible() != null) {
                                state[links].add(LinkState { ls ->
                                    ls[x1] = node.position!!.x
                                    ls[y1] = node.position!!.y
                                    ls[x2] = node.nextVisible()!!.position!!.x
                                    ls[y2] = node.nextVisible()!!.position!!.y
                                })
                            }
                        }
                    }, atop, abottom)
        }
        return res
    }

}

