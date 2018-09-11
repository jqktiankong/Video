package com.jqk.video.view.home

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class MyFrameLayout : FrameLayout {

    interface OnDownListener {
        fun onDown()
    }

    private var onDownListener: OnDownListener? = null

    fun setOnDownListener(l: OnDownListener) {
        this.onDownListener = l
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                onDownListener!!.onDown()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}