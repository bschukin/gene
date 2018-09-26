package org.gene.d2

import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.web.css.css
import kotlinx.html.style
import org.gene.Charges
import org.gene.view.CellState
import org.gene.view.GridState
import org.gene.view.LinkState
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.canvas

class GridComponent(props: GridProps) : RComponent<GridComponent.GridProps, RState>(props) {

    interface GridProps : RProps {
        var gridState: DataMapF<GridState>?
    }

    private var _canvas: dynamic = null

    override fun componentDidMount() {
        //drawBorder()
    }

    override fun RBuilder.render() {
        println("render")
        canvas {
            attrs {
                width = "600px"
                height = "600px"
                style = css {
                    border = "1px solid #AAAAAA"
                }

            }
            ref { _canvas = it }
        }
        drawBorder()
    }

    data class DrawContext(val canvas: dynamic,
                           val context2d: dynamic,
                           val cellsOnEdge: Int,
                           val marginX: Int,
                           val marginY: Int,
                           val labelFreq: Int,
                           val labelSize: Int) {
        val width = canvas.width as Int - marginX * 2
        val height = canvas.height as Int - marginY * 2
        val edge = width / cellsOnEdge
    }

    private fun drawBorder() {
        println("here")
        if(_canvas==null)
            return
        println("here _canvas!=null")
        val context2d = _canvas.getContext("2d")
        context2d.fillStyle = "gray"
        context2d.lineWidth = 1

        context2d.clearRect(0, 0, _canvas.width, _canvas.height);

        val ctx = DrawContext(
                canvas = _canvas, context2d = context2d,
                cellsOnEdge = 16,
                marginX = 20, marginY = 20,
                labelFreq = 2, labelSize = 10)


        drawGrid(ctx)
        val gr = props.gridState
        if (gr != null)
            drawChains(ctx, gr)
    }

    private fun drawChains(ctx: DrawContext, gridState: DataMapF<GridState>) {
        gridState[GridState.cells].forEach { cell ->

            val color = when (cell[CellState.charge]) {
                Charges.A_UNIT -> "blue"
                Charges.A_VU -> "black"
                else -> TODO()
            }
            drawCell(ctx, cell[CellState.x], cell[CellState.y], color)
        }
        gridState[GridState.links].forEach { link ->
            drawLink(ctx, link[LinkState.x1], link[LinkState.y1],
                    link[LinkState.x2], link[LinkState.y2], "blue");
        }
    }

    private fun drawGrid(ctx: DrawContext) {
        ctx.context2d.fillStyle = "gray"
        ctx.context2d.lineWidth = 1

        val width = ctx.canvas.width as Int - ctx.marginX * 2
        val height = ctx.canvas.height as Int - ctx.marginY * 2

        val cellsByX = ctx.cellsOnEdge
        val cellsByY = ctx.cellsOnEdge

        for (i in 0..cellsByX)
            drawLine(ctx.context2d, ctx.marginX + i * ctx.edge + 0.5, ctx.marginY + 0,
                    ctx.marginX + i * ctx.edge + 0.5, ctx.marginY + height,
                    "#D3D3D3")

        for (i in 0..cellsByY) {
            drawLine(ctx.context2d, ctx.marginX + 0, ctx.marginY + i * ctx.edge + 0.5,
                    ctx.marginX + width, ctx.marginY + i * ctx.edge + 0.5,
                    "#D3D3D3");
        }


        for (i in 0..cellsByY / ctx.labelFreq - 1) {
            text(ctx.context2d, ctx.labelSize, (i * ctx.labelFreq).toString(), ctx.marginX / 2 - ctx.labelSize / 2,
                    i * ctx.labelFreq * ctx.edge + ctx.marginY + ctx.edge / 2 - 1);
        }
        for (i in 0..cellsByX / ctx.labelFreq - 1) {
            text(ctx.context2d, ctx.labelSize, (i * ctx.labelFreq).toString(),
                    i * ctx.labelFreq * ctx.edge + ctx.marginX - ctx.labelSize / 2 + ctx.edge / 2,
                    ctx.marginY / 2 - ctx.labelSize / 2);
        }
    }

    fun text(context2d: dynamic, sizepx: Int, text: String, x: Int, y: Int) {
        context2d.font = "${sizepx}px Sans-Serif"
        context2d.textBaseline = "top";
        context2d.fillText(text, x, y);
    }

    fun drawLine(context2d: dynamic, x1: Number, y1: Number, x2: Number, y2: Number, color: String,
                 lineWidth: Int = 1) {

        context2d.fillStyle = "#000"
        context2d.strokeStyle = color

        context2d.beginPath()
        context2d.moveTo(x1, y1)
        context2d.lineTo(x2, y2)
        context2d.lineWidth = lineWidth
        context2d.stroke()
        context2d.closePath()
    }

    private fun drawCell(ctx: DrawContext, x1: Int, y1: Int, color: String) {
        ctx.context2d.fillStyle = color;
        ctx.context2d.fillRect(ctx.marginX + x1 * ctx.edge + 5, ctx.marginY + y1 * ctx.edge + 5, ctx.edge - 10, ctx.edge - 10);

    }

    private fun drawLink(ctx: DrawContext, x1: Int, y1: Int, x2: Int, y2: Int, color: String) {
        val c1 = getPaintCenterOfCell(ctx, x1, y1)
        val c2 = getPaintCenterOfCell(ctx, x2, y2)

        drawLine(ctx.context2d, c1[0], c1[1], c2[0], c2[1], color, 2)
    }


    private fun getPaintCenterOfCell(ctx: DrawContext, x1: Int, y1: Int): List<Int> {
        return listOf(ctx.marginX + (x1 + 1) * ctx.edge - ctx.edge / 2, ctx.marginY + (y1 + 1) * ctx.edge - ctx.edge / 2)
    }

}