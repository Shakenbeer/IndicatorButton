package com.shakenbeer.indicatorbutton

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import kotlinx.android.synthetic.main.indicator_button.view.*

open class CheckableIndicatorButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : IndicatorButton(context, attrs, defStyleAttr), Checkable {

    interface OnCheckedChangeListener {
        fun onCheckedChanged(view: View, isChecked: Boolean)
    }

    private val listeners = mutableListOf<OnCheckedChangeListener>()

    private var checked: Boolean = false

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.IndicatorButton, 0, 0).use {
            isChecked = it.getBoolean(R.styleable.CheckableIndicatorButton_android_checked, isChecked)
        }

        //Step 1: new selectors, that know about checked state
        background = context.getDrawable(R.drawable.checkable_background_selector)
        icon.imageTintList = ContextCompat.getColorStateList(context, R.color.checkable_icon_tint_selector)
        indicator.imageTintList = ContextCompat.getColorStateList(context, R.color.checkable_icon_tint_selector)
        label.setTextColor(ContextCompat.getColorStateList(context, R.color.checkable_text_color_selector))
    }

    fun addOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener) {
        listeners.add(onCheckedChangeListener)
    }

    fun removeOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener) {
        listeners.remove(onCheckedChangeListener)
    }

    // Step 2: override Checkable's methods
    override fun isChecked() = checked

    // Step 2: override Checkable's methods
    override fun toggle() {
        isChecked = !checked
    }

    // Step 2: override Checkable's methods
    override fun setChecked(checked: Boolean) {
        if (this.checked != checked) {
            this.checked = checked
            children.filter { it is Checkable }.forEach { (it as Checkable).isChecked = checked }
            listeners.forEach { it.onCheckedChanged(this, this.checked) }
            refreshDrawableState()
        }
    }

    //Step 3: fire toggle
    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    //Step 4: add checked state to a list of possible states
    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    companion object {
        //Step 4: introduce checked state
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}