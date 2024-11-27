package com.example.kidlearn

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val lines = mutableListOf<Line>()
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    data class Line(
        val startX: Float,
        val startY: Float,
        val endX: Float,
        val endY: Float
    )

    fun addLine(startX: Float, startY: Float, endX: Float, endY: Float) {
        lines.add(Line(startX, startY, endX, endY))
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw all lines
        lines.forEach { line ->
            canvas.drawLine(
                line.startX,
                line.startY,
                line.endX,
                line.endY,
                paint
            )
        }
    }

    // Method to clear all lines
    fun clearLines() {
        lines.clear()
        invalidate()
    }
}