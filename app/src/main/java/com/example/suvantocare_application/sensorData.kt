package com.example.suvantocare_application

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class sensorData @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // your helper variables etc. can be here
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val size = 100

    private var temperature = 0f

    init
    {
        paint.color = Color.BLUE
        textPaint.color = Color.BLACK
        textPaint.textSize = 30.0f
        textPaint.textAlign = Paint.Align.CENTER
        // this is constructor of your component
        // all initializations go here
    }

    fun setTemperature (temp: Float) {
        temperature = temp

        if (temperature > 0) {

            //paint.color = Color.RED
            paint.color = Color.parseColor("#ff4f6c")

        } else {
            paint.color = Color.BLUE
        }

        //uudestaan
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // define the colors!
        //paint.color = Color.BLUE
        textPaint.color = Color.BLACK

        // you can do all the drawing through the canvas-object
        // parameters: x-coordinate, y-coordinate, size, color
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(), paint)

        // parameters: content, x, y, color
        canvas.drawText(temperature.toString(), (width / 2).toFloat(), (height / 2).toFloat() + 25f, textPaint);
    }

    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Try for a width based on our minimum
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        var w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 1)
        // if no exact size given (either dp or match_parent)
        // use this one instead as default (wrap_content)
        if (w == 0)
        {
            w = size * 2
        }
        // Whatever the width ends up being, ask for a height that would let the view
        // get as big as it can
        // val minh: Int = View.MeasureSpec.getSize(w) + paddingBottom + paddingTop
        // in this case, we use the height the same as our width, since it's a circle
        val h: Int = View.resolveSizeAndState(
            View.MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        setMeasuredDimension(w, h)
        // Android uses this to determine the exact size of your component on screen
    }
}