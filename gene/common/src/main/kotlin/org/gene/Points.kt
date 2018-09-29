package org.gene

import kotlin.math.sqrt

data class Point2(
        //координата x
        val x: Int,
        //координата y
        val y: Int) {

    fun distance(v2: Point2): Double {
        return sqrt(((x - v2.x) * (x - v2.x) + (y - v2.y) * (y - v2.y)).toDouble())
    }

    operator fun plus(v2:Vector2):Point2
    {
        return Point2(x + v2.x, y + v2.y)
    }

    operator fun plus(v2:Point2):Point2
    {
        return Point2(x + v2.x, y + v2.y)
    }

    operator fun div(v2:Int):Point2
    {
        return Point2(x / v2, y / v2)
    }
}

data class Vector2(
        //координата x
        val x: Int,
        //координата y
        val y: Int

)
{
    operator fun plus(v2:Vector2):Vector2
    {
        return Vector2(x + v2.x, y + v2.y)
    }

    operator fun plus(v2:Point2):Point2
    {
        return Point2(x + v2.x, y + v2.y)
    }
}

data class TwoPoints(
        //координата x
         val p1: Point2,
        //координата y
        val p2: Point2) {

    override fun equals(other: Any?): Boolean {
        val tp = other as? TwoPoints ?: return false

        if(p1==tp.p1 && p2==tp.p2) return true
        if(p1==tp.p2 && p2==tp.p1) return true

        return false
    }
}

val v_1_1 = Vector2(-1, -1)
val v0_1 = Vector2(0, -1)
val v1_1 = Vector2(1, -1)
val v_10 = Vector2(-1, 0)
val v10 = Vector2(1, 0)
val v_11 = Vector2(-1, 1)
val v01 = Vector2(0, 1)
val v11 = Vector2(1, 1)
val v00 = Vector2(0, 0)



