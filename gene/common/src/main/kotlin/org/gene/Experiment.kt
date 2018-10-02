package org.gene

import com.bftcom.ice.common.maps.DataMapF
import org.gene.init.initSimpleRandomInstance
import org.gene.view.*

interface Experiment2Service {

    fun newExperimentAndState(D: Int, N: Int): DataMapF<GridState>
    fun newExperiment(D: Int, N: Int)
    fun initLineInstance()
    fun getGridStateForView(): DataMapF<GridState>?
    fun clear(): DataMapF<GridState>?

    fun randomMove(): DataMapF<GridState>?
}


class Experiment2(val D: Int, N: Int) {

    val grid = Grid2(D)
    val chain = DasWave(grid, N)

    private val D1 = D - 1

    fun initLineConformation(horisontal: Boolean, startPosition: Point2) {
        chain.initLineInstance(horisontal, startPosition)
    }

    fun initSimpleRandomConformation(startPosition: Point2, nOfTries:Int = 10000):Boolean {
        return chain.initSimpleRandomInstance(startPosition, nOfTries)
    }

    fun reset() {
        grid.reset()
        chain.reset()
    }

    fun getGridStateForView(atop: Point2 = Point2(0, 0), abottom: Point2 = Point2(D1, D1)): DataMapF<GridState> {
        val res = GridState { state ->
            state[id] = 100
            state[top] = PointState.fromPoint(atop)
            state[bottom] = PointState.fromPoint(abottom)

            val thelinks = mutableSetOf<TwoPoints>()

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
                                val n1 = grid.nodePositionOnGrid(node)
                                val n2 = grid.nodePositionOnGrid(node.prevVisible()!!)
                                val tp = TwoPoints(n1, n2)
                                if (thelinks.find { it==tp }==null) {
                                    thelinks.add(tp)
                                    state[links].add(LinkState { ls ->
                                        ls[x1] = n1.x
                                        ls[y1] = n1.y
                                        ls[x2] = n2.x
                                        ls[y2] = n2.y
                                    })
                                }
                            }
                            if (node.nextVisible() != null) {
                                val n1 = grid.nodePositionOnGrid(node)
                                val n2 = grid.nodePositionOnGrid(node.nextVisible()!!)
                                val tp = TwoPoints(n1, n2)
                                if (thelinks.find { it==tp }==null) {
                                    thelinks.add(tp)
                                    state[links].add(LinkState { ls ->
                                        ls[x1] = n1.x
                                        ls[y1] = n1.y
                                        ls[x2] = n2.x
                                        ls[y2] = n2.y
                                    })
                                }
                            }
                        }
                    }, atop, abottom)
        }
        return res
    }
}
