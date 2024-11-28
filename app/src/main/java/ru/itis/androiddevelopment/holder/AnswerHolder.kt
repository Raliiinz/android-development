package ru.itis.androiddevelopment.holder

import androidx.recyclerview.widget.RecyclerView
import ru.itis.androiddevelopment.databinding.ItemAnswerBinding
import ru.itis.androiddevelopment.model.Answer

class AnswerHolder(
    private var viewBinding: ItemAnswerBinding,
    private val onItemChecked: (Int) -> Unit,
    private val onRootClicked: (Int) -> Unit,
    private val checkedColor: Int,
    private val uncheckedColor: Int
):RecyclerView.ViewHolder(viewBinding.root) {

    init {
        viewBinding.pointRb.setOnClickListener {
            onItemChecked.invoke(adapterPosition)
        }
        viewBinding.root.setOnClickListener {
            onRootClicked.invoke(adapterPosition)
        }
    }

    fun bindItem(item: Answer) {
        viewBinding.textTv.text = item.text
        bindIsChecked(item.isChecked)
    }

    fun bindIsChecked(isChecked: Boolean) {
        viewBinding.apply {
            pointRb.isChecked = isChecked
            pointRb.isEnabled = !isChecked
            cardLayout.isChecked = isChecked
            cardLayout.isEnabled = !isChecked
        }

        val backgroundColor = if (isChecked) checkedColor else uncheckedColor
        (viewBinding.root).setCardBackgroundColor(backgroundColor)


//        if (isChecked) {
//            (viewBinding.root).setCardBackgroundColor(
//            ContextCompat.getColor(viewBinding.root.context, R.color.green)
//        )
//        } else {
//            (viewBinding.root).setCardBackgroundColor(
//                ContextCompat.getColor(viewBinding.root.context, R.color.white)
//            )
//        }
    }

}