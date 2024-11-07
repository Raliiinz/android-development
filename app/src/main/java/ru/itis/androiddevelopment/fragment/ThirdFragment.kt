package ru.itis.androiddevelopment.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private var viewBinding: FragmentThirdBinding? = null

    private var inputText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            inputText = savedInstanceState.getString("textView")
            viewBinding?.tvText?.text = inputText
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentThirdBinding.bind(view)

        arguments?.let {
            inputText = if (it.getString(EXTRA_TEXT) == "" || it.getString(EXTRA_TEXT) == getString(R.string.second_screen)) {
                getString(R.string.third_screen)
            }else{
                it.getString(EXTRA_TEXT)
            }
        }

        viewBinding?.tvText?.text = inputText
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textView", inputText)
    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        if (savedInstanceState != null) {
//            inputText = savedInstanceState.getString("textView")
//            viewBinding?.tvText?.text = inputText
//        }
//    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        const val THIRD_TAG = "THIRD_TAG"
        private const val EXTRA_TEXT = "EXTRA_TEXT"

        fun getInstance(
            param: String
        ):ThirdFragment {
            return ThirdFragment().apply {
                arguments = bundleOf(EXTRA_TEXT to param)
            }
        }
    }
}