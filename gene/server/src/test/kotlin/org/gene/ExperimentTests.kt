package org.gene

import org.gene.moving.randomMoveOperator
import org.junit.Test

class ExperimentTests {

    @Test
    public fun testTryRandomMove() {
        val e = Experiment2(16, 4)
        e.initLineConformation(true, Point2(4, 4))

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

    private class NodeStat {
        var hits = 0;
        var success = 0

        fun hit(flag:Boolean) {
            hits++
            if(flag)
                success++
        }
    }

    @Test
    fun testTryRandomMove2() {
        val e = Experiment2(16, 9)
        e.initLineConformation(true, Point2(4, 4))

        val stat = mutableMapOf<Int,NodeStat>()
        e.chain.realNodes.forEach {
            stat[it.index] = NodeStat()
        }


        for (i in 1.. 1000) {
            val node = e.chain.pNodes[getRandomInt(e.chain.N) * 2]
            val succ = e.chain.randomMoveOperator(node)
            stat[node.index]!!.hit(succ)
        }

        stat.forEach { t, u ->
            println("$t-> success = ${u.success}, total = ${u.hits}, % = ${u.success.toDouble()/u.hits}")
        }
    }

    @Test
     fun testTryRandomInstance() {
        val e = Experiment2(16, 55)
        e.initSimpleRandomConformation(Point2(7, 7))

        e.grid.print()
    }

}
