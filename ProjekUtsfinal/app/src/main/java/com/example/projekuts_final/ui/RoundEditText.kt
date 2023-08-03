package com.example.projekuts_final.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class RoundEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val radius = 15f // radius lengkungan sudut
    private val path = Path()
    private val rect = RectF()

    override fun onDraw(canvas: Canvas?) {
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }
}