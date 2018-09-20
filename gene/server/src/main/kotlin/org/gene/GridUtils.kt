package org.gene

import org.gene.Charges.getChargeLogo

fun Grid2.print(atop: Point2 = Point2(0, 0), abottom: Point2 = Point2(D - 1, D - 1)) {

    for (j in atop.y..abottom.y) {
        for (i in atop.x..abottom.x) {
            print(  getChargeLogo(XY[i][j].charge) + " ")
        }
        println()
    }

}