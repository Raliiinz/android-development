package ru.itis.androiddevelopment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.RequestManager
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.databinding.ItemHolderButtonBinding
import ru.itis.androiddevelopment.databinding.ItemHolderFilmListBinding
import ru.itis.androiddevelopment.databinding.ItemHolderFilmGridBinding
import ru.itis.androiddevelopment.databinding.ItemLargeBinding
import ru.itis.androiddevelopment.databinding.ItemSmallBinding
import ru.itis.androiddevelopment.model.HoldersData
import ru.itis.androiddevelopment.repository.ScreensContentRepository
import ru.itis.androiddevelopment.utils.SampleDiffUtil

class ListContentAdapter(
    private val requestManager: RequestManager,
    private val onClick: (HoldersData) -> Unit,
    private val onListButtonClick: () -> Unit,
    private val onGridButtonClick: () -> Unit,
    private val onAnotherButtonClick: () -> Unit,
    private val onItemLongClick: (Int) -> Unit,
    private var isGridView: Boolean,
    private var isStaggeredView: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = mutableListOf<HoldersData>()

    fun getItem(position: Int): HoldersData {
        return dataList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BUTTONS -> {
                val buttonsBinding = ItemHolderButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ButtonsViewHolder(buttonsBinding)
            }
            VIEW_TYPE_GRID -> {
                val gridBinding = ItemHolderFilmGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(gridBinding)
            }
            VIEW_TYPE_LIST -> {
                val listBinding = ItemHolderFilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListViewHolder(listBinding)
            }
            VIEW_TYPE_LARGE -> {
                val largeBinding = ItemLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LargeViewHolder(largeBinding)
            }
            VIEW_TYPE_SMALL -> {
                val smallBinding = ItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SmallViewHolder(smallBinding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isStaggeredView) {
            val layoutParams = holder.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams
//            if (layoutParams != null) {
//                if (isStaggeredView) {
                    if ((position==0|| holder is ButtonsViewHolder || holder is LargeViewHolder)) {
                        layoutParams?.isFullSpan = true

                    } else {
                        layoutParams?.isFullSpan = false
                    }
//                }
//                holder.itemView.layoutParams = layoutParams
//                holder.itemView.requestLayout()
//            }
        }

        when (holder) {
            is ListViewHolder -> {
                val movie = getItem(position)
                holder.bind(movie)
            }

            is GridViewHolder -> {
                val movie = getItem(position)
                holder.bind(movie)
            }

            is ButtonsViewHolder -> {
                holder.bind()
            }

            is LargeViewHolder -> {
                val movie = getItem(position)
                holder.bind(movie)
            }

            is SmallViewHolder -> {
                val movie = getItem(position)
                holder.bind(movie)
            }
        }
    }

    override fun getItemCount() = dataList.size


    inner class ListViewHolder(private val binding: ItemHolderFilmListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        fun bind(movie: HoldersData) {
            binding.headerTv.text = movie.headerText
            requestManager.load(movie.imageUrl)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.pictureIv)
        }

        fun remove() {
            val position = adapterPosition
            ScreensContentRepository.removeFilmById(position)
            updateDate(ScreensContentRepository.getAllFilms())
        }
    }


    inner class GridViewHolder(private val binding: ItemHolderFilmGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }

            binding.root.setOnLongClickListener {
                onItemLongClick(adapterPosition)
                true
            }
        }
        fun bind(movie: HoldersData) {
            binding.headerTv.text = movie.headerText
            requestManager.load(movie.imageUrl)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.pictureIv)
        }
    }

    inner class LargeViewHolder(private val binding: ItemLargeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }

            binding.root.setOnLongClickListener {
                onItemLongClick(adapterPosition)
                true
            }
        }
        fun bind(movie: HoldersData) {
            binding.textView.text = movie.headerText
            requestManager.load(movie.imageUrl)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.imageView)
        }
    }

    inner class SmallViewHolder(private val binding: ItemSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }

            binding.root.setOnLongClickListener {
                onItemLongClick(adapterPosition)
                true
            }
        }
        fun bind(movie: HoldersData) {
            binding.textView.text = movie.headerText
            requestManager.load(movie.imageUrl)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.imageView)
        }
    }


    inner class ButtonsViewHolder(private val binding: ItemHolderButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnFirst.setOnClickListener {
                onListButtonClick()
            }
            binding.btnSecond.setOnClickListener {
                onGridButtonClick()
            }
            binding.btnThird.setOnClickListener {
                onAnotherButtonClick()
            }
        }
    }

    companion object {
        const val VIEW_TYPE_LIST = 0
        const val VIEW_TYPE_GRID = 1
        const val VIEW_TYPE_BUTTONS = 2
        const val VIEW_TYPE_LARGE = 3
        const val VIEW_TYPE_SMALL = 4
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> VIEW_TYPE_BUTTONS
            !isGridView && !isStaggeredView -> VIEW_TYPE_LIST
            isGridView -> VIEW_TYPE_GRID
            isStaggeredView && (position % 3 == 1 ) -> VIEW_TYPE_LARGE
            isStaggeredView -> VIEW_TYPE_SMALL
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun setGridView(isGridView: Boolean) {
        this.isGridView = isGridView
    }

    fun setStaggeredView(isStaggeredView: Boolean) {
        this.isStaggeredView = isStaggeredView
    }


    fun updateDate(list: MutableList<HoldersData>) {
            val diff = SampleDiffUtil(oldList = dataList, newList = list)
            val diffResult = DiffUtil.calculateDiff(diff)
            dataList.clear()
            dataList.addAll(list)
            diffResult.dispatchUpdatesTo(this)
        }
}
