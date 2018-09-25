package org.gene.view

import org.gene.*
import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.common.maps.Field
import com.bftcom.ice.common.maps.MappingFieldSet
import com.bftcom.ice.common.maps.Transience

object PointState : MappingFieldSet<PointState>("PointState", "", null, Transience)
{
    val x = Field.int("x")
    val y = Field.int("y")

    fun fromPoint(p2:Point2):DataMapF<PointState>
    {
        return create {ps->
            ps[x] = p2.x
            ps[y] = p2.y
        }
    }

}

object CellState : MappingFieldSet<CellState>("CellState", "", null, Transience)
{
    val x = Field.intNN("x")
    val y = Field.intNN("y")
    val charge = Field.byte("charge")
}

object LinkState : MappingFieldSet<LinkState>("LinkState", "", null, Transience)
{
    val x1 = Field.intNN("x1")
    val y1 = Field.intNN("y1")
    val x2 = Field.intNN("x2")
    val y2 = Field.intNN("y2")
}

object GridState : MappingFieldSet<GridState>("GridState", "", null, Transience)
{
    val id = Field.id()
    val top = Field.jsonObj("top", PointState)
    val bottom = Field.jsonObj("bottom", PointState)

    val cells  = Field.jsonList("cells", CellState)
    val links  = Field.jsonList("links", LinkState)
}