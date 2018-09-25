package org.gene

import com.bftcom.ice.common.maps.DataMapF
import org.gene.view.GridState
import org.springframework.stereotype.Service

@Service
open class Experiment2ServiceImpl : Experiment2Service {

    var experiment2: Experiment2? = null

    override fun newExperimentAndState():DataMapF<GridState> {

        newExperiment(10, 5)
        initLineInstance()
        return getGridStateForView()
    }


    override fun newExperiment(D: Int, N: Int) {
        experiment2 = Experiment2(D, N)
    }

    override fun initLineInstance() {
        experiment2!!.initLineConformation(true, Point2(0, 0))
    }

    override fun getGridStateForView(): DataMapF<GridState> {
        return experiment2!!.getGridStateForView()
    }

}