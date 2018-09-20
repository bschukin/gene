package org.gene

import com.bftcom.ice.server.util.printAsJson
import org.junit.Assert
import org.junit.Test
import java.util.*

class ExperimentTests {

    @Test
    public fun testCreateLineInstance()
    {
       val e = Experiment2(10, 5)
        e.initLineConformation(true, Point2(0,0))

        //e.getGridStateForView().printAsJson(false)
        e.grid.print()
    }

}
