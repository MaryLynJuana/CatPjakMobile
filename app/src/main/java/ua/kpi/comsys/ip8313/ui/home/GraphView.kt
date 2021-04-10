package ua.kpi.comsys.ip8313.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.kpi.comsys.ip8313.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow

class GraphView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var centerX = 0f
    private var centerY = - 10f
    private var one = 0F
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 8F
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        one = (min(width, height) / 8.0 * 0.1).toFloat()
    }

    private fun drawAxis(canvas: Canvas?, centerX: Float, centerY: Float) = with(canvas!!) {
        val endX = centerX + 10F * one
        val endY = centerY - 27F * one
        drawLines(floatArrayOf(
            centerX - 10 * one, centerY, endX, centerY,
            centerX, centerY + 27F * one, centerX, endY,
            endX, centerY, endX - 25, centerY - 25,
            endX, centerY, endX - 25, centerY + 25,
            centerX, endY, centerX - 25, endY + 25,
            centerX, endY, centerX + 25, endY + 25
        ), paint)
        drawLine(centerX + one, centerY - 10, centerX + one, centerY + 10, paint)
        drawText("3", centerX + 3 * one - 10, centerY + 25, paint)
        drawText("-3", centerX - 3 * one, centerY - 10, paint)
        drawText("27", centerX - 25, centerY - 27 * one + 10, paint)
        drawText("-27", centerX + 10, centerY + 27 * one - 10, paint)
    }

    private fun drawGraph(canvas: Canvas?) = with(canvas!!) {
        var x = - 3F
        while (x <= 3) {
            x += 0.01F
            y = x.pow(3)
            drawPoint(centerX + x * one, centerY - y * one, paint)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        centerX = (width / 2).toFloat()
        centerY = (height / 3).toFloat()
        paint.color = ContextCompat.getColor(context, R.color.black)
        drawAxis(canvas, centerX, centerY)
        paint.color = ContextCompat.getColor(context, R.color.yellow)
        drawGraph(canvas)
    }
}