package com.example.inappupdatedownloadingui.circularprogress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.in_appupdatelibrary.R

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundPaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 16f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val progressPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 14f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.BLUE
        textSize = 20f
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
        isAntiAlias = true
    }

    private var progress = 0
    private var max = 100

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircularProgressBar,
            0, 0
        ).apply {
            try {
                progressPaint.color = getColor(R.styleable.CircularProgressBar_progressColor, Color.BLUE)
                backgroundPaint.color = getColor(R.styleable.CircularProgressBar_backgroundColor, Color.LTGRAY)
                textPaint.textSize = getDimension(R.styleable.CircularProgressBar_textSize, 20f)
                progressPaint.strokeWidth = getDimension(R.styleable.CircularProgressBar_progressStrokeWidth, 14f)
                backgroundPaint.strokeWidth = getDimension(R.styleable.CircularProgressBar_backgroundStrokeWidth, 16f)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2
        val centerY = height / 2
        val strokeWidth = maxOf(backgroundPaint.strokeWidth, progressPaint.strokeWidth)
        val radius = minOf(centerX, centerY) - strokeWidth / 2

        val rectF = RectF(
            (centerX - radius),
            (centerY - radius),
            (centerX + radius),
            (centerY + radius)
        )

        // Draw background arc
        canvas.drawArc(rectF, 0f, 360f, false, backgroundPaint)

        // Draw progress arc
        val sweepAngle = 360f * progress / max
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)

        // Draw text
        val text = "$progress%"
        val fm = textPaint.fontMetrics
        val baseline = centerY - (fm.ascent + fm.descent) / 2
        canvas.drawText(text, centerX.toFloat(), baseline, textPaint)
    }

    fun setProgress(value: Int) {
        progress = value.coerceIn(0, max)
        invalidate()
    }

    fun setMax(value: Int) {
        max = value
        invalidate()
    }

    fun setProgressColor(color: Int) {
        progressPaint.color = color
        textPaint.color = color
        invalidate()
    }

    fun setBackgroundColorDynamic(color: Int) {
        backgroundPaint.color = color
        invalidate()
    }

    fun setStrokeWidth(backgroundStroke: Float, progressStroke: Float) {
        backgroundPaint.strokeWidth = backgroundStroke
        progressPaint.strokeWidth = progressStroke
        invalidate()
    }

    fun setTextSize(sizeInPx: Float) {
        textPaint.textSize = sizeInPx
        invalidate()
    }
}
