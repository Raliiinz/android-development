package ru.itis.androiddevelopment.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.databinding.FragmentListDetailBinding
import ru.itis.androiddevelopment.model.HoldersData
import ru.itis.androiddevelopment.repository.ScreensContentRepository

class ListDetailFragment: Fragment(R.layout.fragment_list_detail) {
    private var viewBinding: FragmentListDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentListDetailBinding.bind(view)

        viewBinding?.let { viewBinding ->
            val contentId = arguments?.getInt(FILM_ID)
            if (contentId != null) {
                val content = ScreensContentRepository.getFilmById(contentId)
                content?.let { setInfo(it) }
            }
        }
    }

    private val options: RequestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)

    private fun setInfo(content: HoldersData) {
        viewBinding?.run {
            titleTextView.text = content.headerText
            descriptionTextView.text = content.descText

            Glide.with(this@ListDetailFragment)
                .load((content as? HoldersData)?.imageUrl)
                .apply(options)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(imageView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        private const val FILM_ID = "FILM_ID"
        private const val FILM_HEADER_TEXT = "FILM_HEADER_TEXT"
        private const val FILM_DESC_TEXT = "FILM_DESC_TEXT"
        private const val FILM_IMAGE_URL = "FILM_IMAGE_URL"
        const val LIST_DETAIL_TAG = "LIST_DETAIL_TAG"

        fun getInstance(filmItem : HoldersData) : ListDetailFragment {
            return ListDetailFragment().apply {
                arguments = bundleOf(
                    FILM_ID to filmItem.id,
                    FILM_HEADER_TEXT to filmItem.headerText,
                    FILM_DESC_TEXT to filmItem.descText,
                    FILM_IMAGE_URL to filmItem.imageUrl
                )
            }
        }
    }
}