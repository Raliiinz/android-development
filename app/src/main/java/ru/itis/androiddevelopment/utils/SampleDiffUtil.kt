package ru.itis.androiddevelopment.utils

import androidx.recyclerview.widget.DiffUtil
import ru.itis.androiddevelopment.model.HoldersData

class SampleDiffUtil(
    private var oldList: List<HoldersData>,
    private var newList: List<HoldersData>
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