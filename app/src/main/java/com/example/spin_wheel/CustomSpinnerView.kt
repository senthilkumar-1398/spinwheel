package com.example.spin_wheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CustomSpinnerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val slices = listOf("Prize 1", "Prize 2", "Prize 3", "Prize 4") // List of prizes
    private val colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW) // Colors for each slice
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG) // Anti-aliased paint
    private var radius = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        radius = min(centerX, centerY) * 0.9f

        // Draw each slice
        val angleStep = 360f / slices.size
        var startAngle = 0f

        for (i in slices.indices) {
            paint.color = colors[i % colors.size]
            canvas.drawArc(
                centerX - radius, centerY - radius,
                centerX + radius, centerY + radius,
                startAngle, angleStep, true, paint
            )
            startAngle += angleStep
        }

        // Draw text on each slice
        startAngle = angleStep / 2
        paint.color = Color.WHITE
        paint.textSize = radius / 10
        for (i in slices.indices) {
            val angleRad = Math.toRadians(startAngle.toDouble())
            val textX = (centerX + radius * 0.5f * cos(angleRad)).toFloat()
            val textY = (centerY + radius * 0.5f * sin(angleRad)).toFloat()
            canvas.drawText(slices[i], textX, textY, paint)
            startAngle += angleStep
        }
    }
}
