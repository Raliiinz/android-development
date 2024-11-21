package ru.itis.androiddevelopment.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.adapter.ListContentAdapter
import ru.itis.androiddevelopment.databinding.DialogBottomSheetBinding
import ru.itis.androiddevelopment.repository.ScreensContentRepository

class BottomSheetFragment(val adapter: ListContentAdapter): BottomSheetDialogFragment(R.layout.dialog_bottom_sheet) {
    private var viewBinding: DialogBottomSheetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = DialogBottomSheetBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        val inputField = viewBinding?.etTextInput

        viewBinding?.apply {
            btnAddRandom.setOnClickListener {
                val count = inputField?.text.toString().toIntOrNull() ?: return@setOnClickListener
                addItemsRandomly(count)
            }

            btnRemoveRandom.setOnClickListener {
                val count = inputField?.text.toString().toIntOrNull() ?: return@setOnClickListener
                removeItemsRandomly(count)
            }

            btnAddToRandomPosition.setOnClickListener {
                addSingleItemRandomly()
            }

            btnRemoveFromRandomPosition.setOnClickListener {
                removeSingleItemRandomly()
            }
        }
    }

    private fun addItemsRandomly(count: Int) {
        ScreensContentRepository.addFilms(count)
        adapter.updateDate(ScreensContentRepository.getAllFilms())
    }

    private fun removeItemsRandomly(count: Int) {
        ScreensContentRepository.removeFilms(count)
        adapter.updateDate(ScreensContentRepository.getAllFilms())
    }

    private fun addSingleItemRandomly() {
        ScreensContentRepository.addFilms(1)
        adapter.updateDate(ScreensContentRepository.getAllFilms())
    }

    private fun removeSingleItemRandomly() {
        ScreensContentRepository.removeFilms(1)
        adapter.updateDate(ScreensContentRepository.getAllFilms())
    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val BOTTOM_SHEET_TAG = "BOTTOM_SHEET_TAG"
    }
}