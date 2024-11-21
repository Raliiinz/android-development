package ru.itis.androiddevelopment.ui.decorators

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.itis.androiddevelopment.adapter.ListContentAdapter

class ItemHorizontalTouch() : ItemTouchHelper(object: SimpleCallback( 0, LEFT) {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val touchFlag = if (viewHolder.adapterPosition > 0) {
            ItemTouchHelper.LEFT
        } else {
            0
        }
        return makeMovementFlags(0, touchFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (viewHolder is ListContentAdapter.ListViewHolder && viewHolder.adapterPosition > 0) {
            viewHolder.remove()
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.66f
    }

    override fun isItemViewSwipeEnabled(): Boolean = true
})
