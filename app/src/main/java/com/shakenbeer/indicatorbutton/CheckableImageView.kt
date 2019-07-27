package com.shakenbeer.indicatorbutton

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class CheckableImageView : ImageView, Checkable {

    private var mChecked = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(b: Boolean) {
        if (b != mChecked) {
            mChecked = b
            refreshDrawableState()
        }
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    companion object {

        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}