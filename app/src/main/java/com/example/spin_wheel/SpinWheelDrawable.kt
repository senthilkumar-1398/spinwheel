package com.example.spin_wheel

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log

class SpinWheelDrawable(
    private val items: List<String>,
    private val partitionCount: Int,
    private val resources: Resources
) : Drawable() {

    private val paint = Paint()
    private val rect = RectF()
    private var spinWheelBitmap: Bitmap? = null
    private var arrowBitmap: Bitmap? = null
    private val partitionColors = listOf(
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN
    )
    private var selectedItemIndex = -1 // Keep track of the selected item index
    private var currentRotationAngle = 0f // Keep track of current rotation

    init {
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.textAlign = Paint.Align.CENTER

        try {
            spinWheelBitmap = BitmapFactory.decodeResource(resources, R.drawable.spin_wheel_image)
            arrowBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_arrow_down) // Use your arrow drawable
        } catch (e: Exception) {
            Log.e("SpinWheelDrawable", "Error decoding resources", e)
        }
    }

    // Set the selected item dynamically based on rotation
    fun setSelectedItem(selectedIndex: Int) {
        this.selectedItemIndex = selectedIndex % partitionCount
        invalidateSelf() // Redraw the drawable
    }

    // Set the current rotation angle for the wheel (used for spinning)
    fun setRotationAngle(angle: Float) {
        this.currentRotationAngle = angle
        invalidateSelf() // Redraw the drawable
    }

    override fun draw(canvas: Canvas) {
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        // Set the size of the spin wheel to 80% of the canvas width
        val wheelDiameter = (canvasWidth * 0.8).toFloat()

        // Calculate the bounding rect of the wheel
        val left = (canvasWidth - wheelDiameter) / 2
        val top = (canvasHeight - wheelDiameter) / 2
        val right = (canvasWidth + wheelDiameter) / 2
        val bottom = (canvasHeight + wheelDiameter) / 2
        rect.set(left, top, right, bottom)

        // Calculate the angle for each partition
        val anglePerPartition = 360f / partitionCount

        // Rotate the canvas based on the current rotation angle (for spin effect)
        canvas.save()
        canvas.rotate(currentRotationAngle, (canvasWidth / 2).toFloat(), (canvasHeight / 2).toFloat())

        // Draw each partition with a different color
        for (i in 0 until partitionCount) {
            // Set partition color, highlight if selected
            paint.color = if (i == selectedItemIndex) Color.YELLOW else partitionColors[i % partitionColors.size]

            canvas.save()
            canvas.rotate(i * anglePerPartition, (canvasWidth / 2).toFloat(), (canvasHeight / 2).toFloat())
            canvas.drawArc(rect, 0f, anglePerPartition, true, paint)
            canvas.restore()
        }

        // Restore the original canvas after rotating
        canvas.restore()

        // Draw the arrow bitmap at the top (static) center of the wheel
        arrowBitmap?.let {
            val arrowWidth = wheelDiameter * 0.1f // Set arrow size relative to the wheel
            val arrowHeight = arrowWidth * (it.height.toFloat() / it.width) // Maintain aspect ratio
            val arrowLeft = (canvasWidth - arrowWidth) / 2
            val arrowTop = top - arrowHeight / 2
            val arrowRight = arrowLeft + arrowWidth
            val arrowBottom = arrowTop + arrowHeight
            val arrowRect = RectF(arrowLeft, arrowTop, arrowRight, arrowBottom)
            canvas.drawBitmap(it, null, arrowRect, paint)
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }
}
