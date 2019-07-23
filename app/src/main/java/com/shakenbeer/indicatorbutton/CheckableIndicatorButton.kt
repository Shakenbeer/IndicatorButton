package com.shakenbeer.indicatorbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import kotlinx.android.synthetic.main.indicator_button.view.*

class CheckableIndicatorButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Checkable {

    companion object {
        //Step 4: introduce checked state
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    private var checked: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.indicator_button, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.IndicatorButton, 0, 0).use {
            isEnabled = it.getBoolean(R.styleable.IndicatorButton_android_enabled, isEnabled)
        }

        background = context.getDrawable(R.drawable.checkable_background_selector)
        icon.imageTintList = ContextCompat.getColorStateList(context, R.color.checkable_icon_tint_selector)
        indicator.imageTintList = ContextCompat.getColorStateList(context, R.color.checkable_icon_tint_selector)
        text.setTextColor(ContextCompat.getColorStateList(context, R.color.checkable_text_color_selector))

        isClickable = true
        isFocusable = true
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }

    //Step 4: add checked state to a list of possible states
    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    //Step 3: fire toggle
    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    // Step 2: overrde Checkable's methods
    override fun isChecked() = checked

    // Step 2: overrde Checkable's methods
    override fun toggle() {
        isChecked = !checked
    }

    // Step 2: overrde Checkable's methods
    override fun setChecked(checked: Boolean) {
        if (this.checked != checked) {
            this.checked = checked
            children.filter { it is Checkable }.forEach { it.isChecked = checked }
            refreshDrawableState()
        }
    }
}
