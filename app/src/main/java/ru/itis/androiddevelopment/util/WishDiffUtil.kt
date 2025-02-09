package ru.itis.androiddevelopment.util

import androidx.recyclerview.widget.DiffUtil
import ru.itis.androiddevelopment.data.db.entities.WishEntity

class WishDiffUtil (
    private var oldList: List<WishEntity>,
    private var newList: List<WishEntity>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].id == (newList[newItemPosition]).id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}