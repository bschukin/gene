package org.gene.d2

import com.bftcom.ice.web.components.form.AbstractEditForm
import com.bftcom.ice.web.components.form.FormProps
import com.bftcom.ice.web.components.form.FormState
import com.bftcom.ice.web.css.css
import kotlinx.html.style
import react.RBuilder
import react.dom.canvas


class D2BoardPage(props: FormProps) : AbstractEditForm<FormProps, D2BoardPage.State>(props) {

    interface State : FormState {
        var xLength: Int
        var yLength: Int
    }

    init {
        state.xLength = 20
        state.yLength = 20
    }

    var _canvas: dynamic = null

    override fun componentDidMount() {
        super.componentDidMount()
        drawBorder()

    }


    override fun RBuilder.render() {

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
    }

    data class GridOptions(val canvas:dynamic, val context2d:dynamic,
                           val width:Int, val height:Int)

    private fun drawBorder() {
        val context2d = _canvas.getContext("2d")
        context2d.fillStyle = "gray"
        context2d.lineWidth = 1

        val labelfreq = 2
        val marginX = 20
        val marginY = 20

        val neededCellsOnx = 20
        val labelSize = 10


        val width = _canvas.width as Int - marginX * 2
        val height = _canvas.height as Int - marginY * 2

        val edge = width / neededCellsOnx

        drawGrid(marginX, marginY, neededCellsOnx, context2d, labelfreq, labelSize)
        for (i in 5.. 10)
            drawCell(context2d, edge, marginX, marginY, i, 5, "blue")
        for (i in 5..10)
            drawCell(context2d, edge, marginX, marginY, 14, i, "black");

        drawLink(context2d, edge, marginX,marginY,5, 5, 6, 5, "blue");
        drawLink(context2d, edge, marginX,marginY,6, 5, 7, 5, "blue");
    }

    private fun drawGrid(marginX: Int, marginY: Int, neededCellsOnx: Int, context2d: dynamic, labelfreq: Int, labelSize: Int) {
        context2d.fillStyle = "gray"
        context2d.lineWidth = 1

        val width = _canvas.width as Int - marginX * 2
        val height = _canvas.height as Int - marginY * 2

        val edge = width / neededCellsOnx
        val cellsByX = neededCellsOnx
        val cellsByY = neededCellsOnx

        for (i in 0..cellsByX)
            drawLine(context2d, marginX + i * edge + 0.5, marginY + 0,
                    marginX + i * edge + 0.5, marginY + height,
                    "#D3D3D3")

        for (i in 0..cellsByY) {
            drawLine(context2d, marginX + 0, marginY + i * edge + 0.5, marginX + width, marginY + i * edge + 0.5,
                    "#D3D3D3");
        }


        for (i in 0..cellsByY / labelfreq - 1) {
            text(context2d, labelSize, (i * labelfreq).toString(), marginX / 2 - labelSize / 2, i * labelfreq * edge + marginY + edge / 2 - 1);
        }
        for (i in 0..cellsByX / labelfreq - 1) {
            text(context2d, labelSize, (i * labelfreq).toString(), i * labelfreq * edge + marginX - labelSize / 2 + edge / 2,
                    marginY / 2 - labelSize / 2);
        }
    }

    fun text(context2d: dynamic, sizepx: Int, text: String, x: Int, y: Int) {
        context2d.font = "${sizepx}px Sans-Serif"
        context2d.textBaseline = "top";
        context2d.fillText(text, x, y);
    }

    fun drawLine(context2d: dynamic, x1: Number, y1: Number, x2: Number, y2: Number, color: String,
                 lineWidth:Int= 1) {

        context2d.fillStyle = "#000"
        context2d.strokeStyle = color

        context2d.beginPath()
        context2d.moveTo(x1, y1)
        context2d.lineTo(x2, y2)
        context2d.lineWidth = lineWidth
        context2d.stroke()
        context2d.closePath()
    }

    fun drawCell(context2d: dynamic, edge:Int, marginX: Int, marginY: Int, x1:Int, y1:Int, color:String) {
        context2d.fillStyle = color;
        context2d.fillRect(marginX + x1 * edge + 3, marginY + y1 * edge + 3, edge - 6, edge - 6);

    }

    fun drawLink(context2d: dynamic, edge:Int, marginX: Int, marginY: Int, x1:Int, y1:Int, x2:Int, y2:Int, color:String) {
        var c1 = getPaintCenterOfCell(edge, marginX, marginY, x1, y1)
        var c2 = getPaintCenterOfCell(edge, marginX, marginY, x2, y2)

        drawLine(context2d, c1[0],c1[1], c2[0],c2[1], color,2)
    }


    fun getPaintCenterOfCell(edge:Int, marginX: Int, marginY: Int, x1:Int, y1:Int):List<Int> {
        return listOf(marginX + (x1 + 1) * edge - edge / 2, marginY + (y1 + 1) * edge - edge / 2)
    }

}