package com.example.suvantocare_application.muRata

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.example.suvantocare_application.R

class muRataData @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var maxSize = 5
    init
    {
        this.orientation = VERTICAL
        // mitataan viiden TextViewin tarvittema tila
        var someTextView : TextView = TextView(context)
        someTextView.measure(0,0)
        var totalHeight = someTextView.measuredHeight * 5
        // mitataan myös ylimääräinen LinearLayoutin pystysuuntatila
        // esim. paddingien ym. aiheuttama
        this.measure(0, 0)
        var additionalHeight = this.measuredHeight
        this.minimumHeight = totalHeight + additionalHeight
    }

    fun addData(message: String)
    {
        var newTextView : TextView = TextView(context)
        newTextView.text = message
        newTextView.setBackgroundColor(Color.BLACK)
        newTextView.setTextColor(Color.YELLOW)
        while(this.childCount >= maxSize)
        {
            this.removeViewAt(0)
        }
        // ladataan yksinkertainen fadein -animaatio kansiosta
        // res -> anim -> fadein.xml
        val animation = AnimationUtils.loadAnimation(context, R.anim.fadein)
        newTextView.startAnimation(animation)
        this.addView(newTextView)
    }
}