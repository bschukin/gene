package org.gene

import org.junit.Test
import java.lang.Math.round
import kotlin.test.assertEquals
import kotlin.test.assertTrue

public class UrandTest {

    @Test
    fun testUrandSequence() {
        Urand.reset()
        assertTrue(round(urand(), 12) == 0.443204397801)
        assertTrue(round(urand(), 12)== 0.337725165300)
        assertTrue(round(urand(), 12)== 0.042556466069)
        assertTrue(round(urand(), 12)== 0.479015944526)
        assertTrue(round(urand(), 12)== 0.885714501608)
        assertTrue(round(urand(), 12)== 0.020697222091)
        assertTrue(round(urand(), 12)== 0.182299874257)
        assertTrue(round(urand(), 12)== 0.330934192985)
        assertTrue(round(urand(), 12)== 0.168867147062)
        assertTrue(round(urand(), 12)== 0.863359867595)

        Urand.reset()
        assertTrue(round(urand(), 12) == 0.443204397801)
    }

    @Test
    fun testUniformness() {
        Urand.reset()
        val counters = mutableMapOf<Int, Int>()
        for(i in 0..9   )
            counters[i]  = 0

        val N = 10000000
        for(i in 1..N) {
            val random = getRandomInt(10)
            counters[random] = counters[random]!! + 1
        }
        counters.forEach { t, u ->
            println("$t = ${u/N.toDouble()}")
        }

    }

    @Test
    fun testIntSequence() {
        Urand.reset()

        assertEquals(getRandomInt(10), 4)
        assertEquals(getRandomInt(10), 3)
        assertEquals(getRandomInt(10), 0)
        assertEquals(getRandomInt(10), 4)
        assertEquals(getRandomInt(10), 8)
        assertEquals(getRandomInt(10), 0)
        assertEquals(getRandomInt(10), 1)
        assertEquals(getRandomInt(10), 3)
        assertEquals(getRandomInt(10), 1)
        assertEquals(getRandomInt(10), 8)
        assertEquals(getRandomInt(10), 9)
    }

    @Test
    fun testPointOperations() {
        var v1 = Point2(1, 1)
        var v2 = Point2(1, 2)

        assertEquals(distance(v1, v2), 1.0)



    }


}

