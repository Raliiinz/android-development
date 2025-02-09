package ru.itis.androiddevelopment.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.data.db.converters.Converters
import ru.itis.androiddevelopment.data.db.entities.WishEntity
import ru.itis.androiddevelopment.databinding.ItemWishBinding
import ru.itis.androiddevelopment.util.WishDiffUtil

class WishAdapter(
    private val onDeleteClick: (WishEntity) -> Unit
) : RecyclerView.Adapter<WishAdapter.WishViewHolder>() {

    private var wishes = mutableListOf<WishEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        val binding = ItemWishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishViewHolder(binding)
    }

    override fun getItemCount(): Int = wishes.size

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        holder.bind(wishes[position])
    }

    inner class WishViewHolder(private val binding: ItemWishBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnLongClickListener {
                onDeleteClick(wish)
                true
            }
        }

        fun bind(wish: WishEntity) {
            binding.textViewWishName.text = wish.wishName
            binding.textViewPrice.text = binding.root.context.getString(R.string.price, wish.price.toString())

            wish.photo?.let { byteArray ->
                val bitmap = Converters().toBitmap(byteArray)
                binding.imageViewWishPhoto.setImageBitmap(bitmap)
            } ?: run {
                binding.imageViewWishPhoto.setImageResource(R.drawable.ic_insert_photo)
            }

        }
    }

    fun updateDate(list: MutableList<WishEntity>) {
        val diff = WishDiffUtil(oldList = wishes, newList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        wishes.clear()
        wishes.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(wish: WishEntity): Int? {
        val position = wishes.indexOfFirst { it.id == wish.id }
        if (position != -1) {
            wishes.removeAt(position)
            return position
        }
        return null
    }
}
