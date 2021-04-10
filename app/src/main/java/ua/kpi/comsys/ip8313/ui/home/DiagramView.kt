package ua.kpi.comsys.ip8313.ui.home

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.kpi.comsys.ip8313.R
import kotlin.math.min


private enum class Portion(val circlePercent: Int, val color: Int) {
    YELLOW(15, R.color.yellow),
    BROWN(25, R.color.brown),
    GRAY(45, R.color.gray),
    RED(10, R.color.red),
    PURPLE(5, R.color.purple)
}

class DiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var outerRadius = 0F
    private var innerRadius = 0F
    private var centerX = 0
    private var centerY = 0
    private val oval = RectF()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private fun calculateSweepAngle(portion: Portion) : Float {
        return portion.circlePercent * 3.6f
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        outerRadius = (min(width, height) / 2.0 * 0.5).toFloat()
        innerRadius = outerRadius / 2
        centerX = width / 2
        centerY = height / 2
        oval.set(
            centerX - outerRadius,
            centerY - outerRadius,
            centerX + outerRadius,
            centerY + outerRadius
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var currentAngle = 0F
        for (portion in Portion.values()) {
            val sweepAngle = calculateSweepAngle(portion)
            paint.color = ContextCompat.getColor(context, portion.color)
            canvas?.drawArc(oval, currentAngle, sweepAngle, true, paint)
            currentAngle += sweepAngle
        }
        paint.color = ContextCompat.getColor(context, R.color.dark_grey)
        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), innerRadius, paint)
    }
}