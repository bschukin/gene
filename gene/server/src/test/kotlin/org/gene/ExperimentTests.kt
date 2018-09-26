package org.gene

import com.bftcom.ice.server.util.printAsJson
import org.junit.Assert
import org.junit.Test
import java.util.*

class ExperimentTests {

    @Test
    public fun testCreateLineInstance()
    {
       val e = Experiment2(16, 4)
        e.initLineConformation(true, Point2(4,4))

        e.grid.print()
        println("=========")
        var x = e.chain.tryRandomMove()
        println(x)
        //e.getGridStateForView().printAsJson(false)
        //e.grid.print()

        println("=========")
         x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()

        println("=========")
        x = e.chain.tryRandomMove()
        println(x)
        e.grid.print()
    }

}
