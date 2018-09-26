package org.gene

import org.junit.Assert
import org.junit.Test
import java.util.*

class DasWaveTests {


    @Test
    public fun testMath()
    {
        val D = 16
        val D1 = 15
        //println( D1 and 0)
        /*println( D1 and 1)
        println( D1 and 2)
        println( D1 and 14)*/
        println( D1 and -1)

    }

    @Test
    public fun testCreateLineInstance()
    {
        //создаем решетку [-10; 10]
        val grid = Grid2(10)

        //Создаем цепь длиной 5 в кооординатах (0,0)
        val daswave = DasWave(grid, 5)
        daswave.initLineInstance(false, Point2(0, 0))

        daswave.printCoorinates()

        daswave.pNodes.forEach {
            println(it.toString())
        }

    }

}
