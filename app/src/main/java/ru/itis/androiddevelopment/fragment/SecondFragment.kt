package ru.itis.androiddevelopment.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var viewBinding: FragmentSecondBinding? = null

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
        viewBinding = FragmentSecondBinding.bind(view)

        arguments?.let {
            inputText = if (it.getString(EXTRA_TEXT) == "") {
                getString(R.string.second_screen)
            }else{
                it.getString(EXTRA_TEXT)
            }
        }

        viewBinding?.tvText?.text = inputText

        initViews()

    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }


    private fun initViews() {
        viewBinding?.apply {
            btnFirstSecondFragment.setOnClickListener {
                (requireContext() as? MainActivity)?.navigate(
                    destination = ThirdFragment.getInstance(param = inputText ?: ""),
                    destinationTag = ThirdFragment.THIRD_TAG,
                    action = NavigationAction.REPLACE
                )
            }
        }
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

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {
        private const val EXTRA_TEXT = "EXTRA_TEXT"
        const val SECOND_TAG = "SECOND_TAG"

        fun getInstance(
            param: String
        ):SecondFragment {
            return SecondFragment().apply {
                arguments = bundleOf(EXTRA_TEXT to param)
            }
        }
    }
}