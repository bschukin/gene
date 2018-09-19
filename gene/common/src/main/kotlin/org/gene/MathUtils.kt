package org.gene

import com.bftcom.ice.common.general.SomethingNotFound
import com.bftcom.ice.common.general.throwImpossible
import kotlin.math.round

/**
 * Быстрый Генератор случайных чисел для униформного распределения
 */
object Urand {
    //randomizer
    private val start_seed = 397341824
    private val m2 = 1073741824
    private val mic = 1693666955
    private val ia = 843314861
    private val ic = 453816693
    private val s = 4.65661287307739E-10

    private var seed = start_seed

    //получить случайное число в униформном распределении
    fun urand(): Double {
        //static seed=397341824;
        seed = seed * ia
        if (seed > mic) seed = seed - m2 - m2
        seed = seed + ic
        if (ic shr 1 > m2) seed = seed - m2 - m2
        if (seed < 0) seed = seed + m2 + m2
        return seed * s
    }

    fun reset() {
        seed = start_seed
    }


    fun GetRandomMoveVector8(): Vector2 {
        when (getRandomInt(8)) {
            0 -> return v_1_1
            1 -> return v0_1
            2 -> return v1_1
            3 -> return v_10
            4 -> return v10
            5 -> return v_11
            6 -> return v01
            7 -> return v11
        }
        throw throwImpossible()
    }



}

fun urand() = Urand.urand()

//Функция возвращаеь случайное целое число в интервале [0, count-1]
fun getRandomInt(count: Int): Int {
    return (count * Urand.urand()).toInt()
}

fun getRandomPointInBox(Top: Vector2, Bot: Vector2): Vector2 {
    return Vector2(((Bot.x + 1 - Top.x) * Urand.urand()).toInt() + Top.x,
            ((Bot.y + 1 - Top.y) * Urand.urand()).toInt() + Top.y)
}

fun getRandomPointInBox(Top: Point2, Bot: Point2): Point2 {
    return Point2(((Bot.x + 1 - Top.x) * Urand.urand()).toInt() + Top.x,
            ((Bot.y + 1 - Top.y) * Urand.urand()).toInt() + Top.y)
}

fun distance(v1: Point2, v2: Point2): Double {
    return v1.distance(v2)
}

fun round(d: Double, decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(d * multiplier) / multiplier
}

