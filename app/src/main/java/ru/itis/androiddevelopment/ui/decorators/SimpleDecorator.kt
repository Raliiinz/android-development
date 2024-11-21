package ru.itis.androiddevelopment.ui.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimpleDecorator(
    private val marginValue: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
            outRect.apply {
                top = marginValue
                bottom =marginValue
                left = marginValue
                right = marginValue

            }
    }
}