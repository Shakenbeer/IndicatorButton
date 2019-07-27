package com.shakenbeer.indicatorbutton

import android.content.Context
import android.util.AttributeSet

class RadioIndicatorButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableIndicatorButton(context, attrs, defStyleAttr){

    override fun performClick(): Boolean {
        if (isChecked) return false
        return super.performClick()
    }
}