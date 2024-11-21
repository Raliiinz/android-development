package ru.itis.androiddevelopment.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.adapter.ListContentAdapter
import ru.itis.androiddevelopment.adapter.ListContentAdapter.Companion.VIEW_TYPE_BUTTONS
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.databinding.FragmentListContentBinding
import ru.itis.androiddevelopment.repository.ScreensContentRepository
import ru.itis.androiddevelopment.ui.decorators.ItemHorizontalTouch
import ru.itis.androiddevelopment.ui.decorators.SimpleDecorator
import ru.itis.androiddevelopment.utils.getValueInDp


class ListContentFragment: Fragment(R.layout.fragment_list_content) {

    private var viewBinding: FragmentListContentBinding? = null
    private var rvAdapter: ListContentAdapter? = null
    private var isGridView = false
    private var isStaggeredView = false
    private val onItemTouchCallback = ItemHorizontalTouch()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentListContentBinding.bind(view)

        val glide = Glide.with(requireContext())
        initRecyclerView(glide)
        initViews()
    }

    private fun initRecyclerView(requestManager: RequestManager) {
        rvAdapter = ListContentAdapter(
            requestManager = requestManager,
            onClick = { content ->
                (requireActivity() as MainActivity).navigate(
                    destination = ListDetailFragment.getInstance(content),
                    destinationTag = ListDetailFragment.LIST_DETAIL_TAG,
                    action = NavigationAction.REPLACE,
                    isAddToBackStack = true)
            },
            onListButtonClick = {switchToListView()},
            onGridButtonClick = {switchToGridView()},
            onAnotherButtonClick = {switchToAnotherView()},
            onItemLongClick =  { position ->
                if (isGridView) {
                    showDeleteConfirmationDialog(position)
                }
            },
            isGridView = isGridView,
            isStaggeredView = isStaggeredView)

        if (isGridView) {
            switchToGridView()
        }else if(isStaggeredView){
            switchToAnotherView()
        }else{
            switchToListView()
        }

        viewBinding?.mainRecycler?.adapter = rvAdapter
        val movies = ScreensContentRepository.getFilms(25)
        rvAdapter?.updateDate(movies)
        viewBinding?.mainRecycler?.addItemDecoration(
            SimpleDecorator(
                marginValue = getValueInDp(value = 8f, requireContext()).toInt()
            )
        )
    }

    private fun switchToListView() {
        isGridView = false
        isStaggeredView = false
        viewBinding?.mainRecycler?.layoutManager = LinearLayoutManager(requireContext())
        onItemTouchCallback.attachToRecyclerView(viewBinding?.mainRecycler)
        rvAdapter?.notifyDataSetChanged()
        rvAdapter?.setGridView(isGridView)
        rvAdapter?.setStaggeredView(isStaggeredView)
    }

    private fun switchToGridView() {
        isGridView = true
        isStaggeredView = false
        val gridLayoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (rvAdapter?.getItemViewType(position)) {
                    VIEW_TYPE_BUTTONS -> 3
                    else -> 1
                }
            }
        }
        viewBinding?.mainRecycler?.layoutManager = gridLayoutManager
        onItemTouchCallback.attachToRecyclerView(null)
        rvAdapter?.notifyDataSetChanged()
        rvAdapter?.setGridView(isGridView)
        rvAdapter?.setStaggeredView(isStaggeredView)
    }

    private fun switchToAnotherView() {
        isStaggeredView = true
        isGridView = false
        rvAdapter?.setStaggeredView(isStaggeredView)
        rvAdapter?.setGridView(isGridView)
        viewBinding?.mainRecycler?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        onItemTouchCallback.attachToRecyclerView(null)
        rvAdapter?.notifyDataSetChanged()
    }

    private fun initViews() {
        viewBinding?.fab?.setOnClickListener {
            val dialog = rvAdapter?.let { it1 ->
                BottomSheetFragment(it1).apply {
                    isCancelable = true
                }
            }
            dialog?.show(childFragmentManager, BottomSheetFragment.BOTTOM_SHEET_TAG)
        }
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Вы уверены, что хотите удалить этот элемент?")
            .setPositiveButton("Удалить") { dialog, id ->
                ScreensContentRepository.removeFilmById(position)
                rvAdapter?.updateDate(ScreensContentRepository.getAllFilms())
            }
            .setNegativeButton("Отмена") { dialog, id ->
            }
        builder.create().show()
    }

    companion object {
        const val FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG"
    }
}
