package org.gene

import com.bftcom.ice.common.maps.DataMapF
import org.gene.view.GridState
import org.springframework.stereotype.Service

@Service
open class Experiment2ServiceImpl : Experiment2Service {

    var experiment2: Experiment2? = null

    override fun newExperimentAndState(D: Int, N: Int): DataMapF<GridState> {

        newExperiment(D, N)
        initSimpleRandomInstance()
        return getGridStateForView()!!
    }


    override fun newExperiment(D: Int, N: Int) {
        experiment2 = Experiment2(D, N)
    }

    override fun initLineInstance() {
        experiment2!!.initLineConformation(true, Point2(0, 0))
    }

    fun initSimpleRandomInstance() {
        experiment2!!.initSimpleRandomConformation(Point2(experiment2!!.D/2, experiment2!!.D/2), 100000)
    }

    override fun getGridStateForView(): DataMapF<GridState>? {
        return experiment2?.getGridStateForView()
    }

    override fun randomMove(): DataMapF<GridState>? {
        assert(experiment2 != null)

        //for (i in 1..100)
            while (!experiment2!!.chain.tryRandomMove()) {
            }

        return getGridStateForView()
    }


    override fun clear(): DataMapF<GridState>? {
        experiment2 = null
        return null
    }


}