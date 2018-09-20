package org.gene

import org.junit.Assert
import org.junit.Test

class GridTests {

    @Test
    fun testCreateAndDestroyGrid() {
        val grid2 = Grid2(10)

        Assert.assertNotNull(grid2.XY)
        Assert.assertNotNull(grid2.top)

        Assert.assertEquals(grid2.XY.size, 10) //D

        Assert.assertNotNull(grid2.XY[1][1])
        Assert.assertTrue(grid2.XY[1][1].point == Point2(1,1))

        Assert.assertNotNull(grid2.XY[9][7])
        Assert.assertTrue(grid2.XY[9][7].point == Point2(9,7))

        Assert.assertEquals(grid2.XY[3][4].charge, Charges.FREE)

        //уничтожение
        grid2.reset()

    }

}
