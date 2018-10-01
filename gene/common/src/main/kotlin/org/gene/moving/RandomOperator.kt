package org.gene.moving

import com.bftcom.ice.common.general.makeSure
import org.gene.*

fun DasWave.randomMoveOperator(pBufNode: Node2): Boolean {
    makeSure(pBufNode.isReal())
    val newPos = Urand.GetRandomMoveVector8() + pBufNode.position!!

    //---!pFirst-&&-!pLast-----------------------------------
    //1 - если ячейка занята и на ней не стоит свой ВЗ - выходим
    if ((XY.getChargeXY(newPos) != Charges.FREE) &&
            (newPos != pBufNode.prevVirt()?.position) && (newPos != pBufNode.next()?.position))
        return false

    //2 - проверка на расстояние
    val distNext = dist1(newPos, pBufNode.nextReal()?.position)
    val distPrev = dist1(newPos, pBufNode.prevReal()?.position)
    if ((distNext>3) || (distPrev>3)) return false;
    freeInnerAndItsVirtuals(pBufNode)

    val ctx = DasWave.SelectContext()
    selectPrevVirtPos(pBufNode, newPos, distPrev, distNext, ctx)
    if(ctx.canSelect)
    {
        pBufNode.position = newPos
        pBufNode.prevVirt()?.position = ctx.vPrev
        pBufNode.nextVirt()?.position = ctx.vNext
    }
    chargeInnerAndItsVirtuals(pBufNode)

    return ctx.canSelect
}