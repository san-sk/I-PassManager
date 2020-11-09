package com.san.ipassmanager.other

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

internal class CenteredToolbar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        androidx.appcompat.R.attr.toolbarStyle
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        val childCount = childCount
        for (i in 0 until childCount) {
            val view = this.getChildAt(i)
            if (view is TextView) {
                 forceTitleCenter(view, l, r)
                break
            }
        }
    }

    /**
     * Centering the layout.
     *
     * @param view The view to be centered
     */
    private fun forceTitleCenter(view: TextView, l: Int, r: Int) {
        val top = view.top
        val bottom = view.bottom
        view.layout(l, top, r, bottom)
        /*navigationIcon?.let {
            view.setPadding(it.intrinsicWidth, 0, 0, 0)
        }*/
        view.setPadding(0, 0, 0, 0)
        view.gravity = Gravity.CENTER
        view.isAllCaps = true
    }
}