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

class GraphView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var centerX = 0f
    private var centerY = 0f
    private var one = 0F
    private val π = PI.toFloat()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 8F
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        one = (min(width, height) / 8.0 * 0.8).toFloat()
    }

    private fun drawAxis(canvas: Canvas?, centerX: Float, centerY: Float) = with(canvas!!) {
        val endX = centerX + π * one
        val endY = centerY - 2F * one
        drawLines(floatArrayOf(
            centerX - π * one, centerY, endX, centerY,
            centerX, centerY + 2F * one, centerX, endY,
            endX, centerY, endX - 25, centerY - 25,
            endX, centerY, endX - 25, centerY + 25,
            centerX, endY, centerX - 25, endY + 25,
            centerX, endY, centerX + 25, endY + 25
        ), paint)
        drawLine(centerX + one, centerY - 10, centerX + one, centerY + 10, paint)
        drawText("π/2", centerX + π / 2 * one - 10, centerY + 25, paint)
        drawText("-π/2", centerX - π / 2 * one, centerY + 25, paint)
        drawText("1", centerX - 25, centerY - one - 10, paint)
        drawText("1", centerX + one, centerY + 25, paint)
        drawText("0", centerX - 25, centerY + 25, paint)
    }

    private fun drawGraph(canvas: Canvas?) = with(canvas!!) {
        var x = - PI.toFloat()
        for (i in 0..360) {
            x += 2 * π / 360
            y = cos(x)
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