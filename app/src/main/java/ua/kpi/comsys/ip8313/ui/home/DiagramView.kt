package ua.kpi.comsys.ip8313.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.kpi.comsys.ip8313.R
import kotlin.math.min


private enum class Portion(val circlePercent: Int, val color: Int) {
    LIGHT_BLUE(45, R.color.light_blue),
    PURPLE(5, R.color.purple),
    YELLOW(25, R.color.yellow),
    GRAY(25, R.color.gray)
}

class DiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var outerRadius = 0.0f
    private var innerRadius = 0.0f
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
        outerRadius = (min(width, height) / 2.0 * 0.8).toFloat()
        innerRadius = outerRadius / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        centerX = canvas!!.width / 2
        centerY = canvas!!.height / 2
        oval.set(
            centerX - outerRadius,
            centerY - outerRadius,
            centerX + outerRadius,
            centerY + outerRadius
        )
        var currentAngle = 0F
        for (portion in Portion.values()) {
            val sweepAngle = calculateSweepAngle(portion)
            paint.color = ContextCompat.getColor(context, portion.color)
            canvas?.drawArc(oval, currentAngle, sweepAngle, true, paint)
            currentAngle += sweepAngle
        }
        val backgroundColor = R.color.dark_grey
        paint.color = ContextCompat.getColor(context, backgroundColor)
        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), innerRadius, paint)
    }
}