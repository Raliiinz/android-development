package ru.itis.androiddevelopment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.androiddevelopment.databinding.ItemAnswerBinding
import ru.itis.androiddevelopment.holder.AnswerHolder
import ru.itis.androiddevelopment.model.Answer

class AnswerAdapter(
    val items: MutableList<Answer>,
    private val onItemChecked: (Int) -> Unit,
    private val onRootClicked: (Int) -> Unit,
    private val checkedColor: Int,
    private val uncheckedColor: Int
) : RecyclerView.Adapter<AnswerHolder>() {

    private val dataList = mutableListOf<Answer>()

    init {
        dataList.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(
            viewBinding = ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemChecked = onItemChecked,
            onRootClicked = onRootClicked,
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        (holder as? AnswerHolder)?.bindItem(dataList[position])
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty ()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (payloads.first()) {
                is Boolean ->
                    (holder as? AnswerHolder)?.bindIsChecked(isChecked = payloads.first() as Boolean)
                else -> super.onBindViewHolder(holder, position, payloads)
            }
        }
    }
}