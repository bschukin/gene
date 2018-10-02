package org.gene.init

import com.bftcom.ice.common.general.makeSure
import org.gene.*


fun DasWave.initSimpleRandomInstance(center: Point2, NOfTries: Int): Boolean {

    if (this.XY.getXY(center) != Charges.FREE) return false

    for (tries in 1..NOfTries) {
        if (this.tryInitRandomInstance(center)) {
            makeSure( this.realNodes.find { it.position==null }==null)
            makeSure( this.virtualNodes.find { it.position!=null }==null)
            println("=====$tries")
            return true
        }
    }
    println("((((((((")
    return false
}

private fun DasWave.tryInitRandomInstance(centerPosition: Point2): Boolean {

    this.pNodes[this.centerNodeIndex].setPositionOnGrid(centerPosition)
    var isNewLast = true
    var isDoneLast = false
    var isDoneFirst = false

    var last: Node2? = this.pNodes[this.centerNodeIndex]
    var first: Node2? = this.pNodes[this.centerNodeIndex]

    while (!(isDoneLast && isDoneFirst)) {
        if (isNewLast && !isDoneLast) {
            last = last?.nextReal()
            if (last == null)
                isDoneLast = true
            else
                if (!putNewLast(last))
                    break
        } else {
            first = first?.prevReal()
            if (first == null)
                isDoneFirst = true
            else
                if (!putNewFirst(first))
                    break
        }
        isNewLast = !isNewLast
    }

    if (!(isDoneFirst && isDoneLast))
        this.reset()
    return isDoneFirst && isDoneLast
}

private fun DasWave.putNewLast(last: Node2): Boolean {

    for (j in 0..15) {
        val pos = last.prevReal()!!.position!! + getRandomMoveVector4()
        if (XY.getXY(pos) == Charges.FREE) {
            last.setPositionOnGrid(pos)
            return true
        }
    }//for (int j=0; ; j++)
    return false
}

private fun DasWave.putNewFirst(first: Node2): Boolean {

    for (j in 0..15) {
        val pos = first.nextReal()!!
                .position!! + getRandomMoveVector4()
        if (XY.getXY(pos) == Charges.FREE) {
            first.setPositionOnGrid(pos)
            return true
        }
    }//for (int j=0; ; j++)
    return false
}