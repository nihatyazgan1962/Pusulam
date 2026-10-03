package com.nihatyazgan.pusulam.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class WaterCompassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var currentMl = 1250
    private var targetMl = 2500

    private val bgRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#1E293B")
        strokeWidth = 22f
        strokeCap = Paint.Cap.ROUND
    }

    private val waterRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#38BDF8") // Parlak Su Mavisi
        strokeWidth = 22f
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 42f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private val subTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#94A3B8")
        textSize = 22f
        textAlign = Paint.Align.CENTER
    }

    fun setWater(current: Int, target: Int = 2500) {
        currentMl = current
        targetMl = target
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = (Math.min(width, height) / 2f) - 30f

        val rect = RectF(cx - radius, cy - radius, cx + radius, cy + radius)
        canvas.drawArc(rect, 135f, 270f, false, bgRingPaint)

        val progress = Math.min(1f, currentMl.toFloat() / targetMl.toFloat())
        val sweepAngle = 270f * progress
        canvas.drawArc(rect, 135f, sweepAngle, false, waterRingPaint)

        canvas.drawText("$currentMl ml", cx, cy - 5f, textPaint)
        canvas.drawText("Hedef: $targetMl ml", cx, cy + 35f, subTextPaint)
    }
}
