package ru.itis.androiddevelopment.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.bottomsheet.BottomSheetFragment
import ru.itis.androiddevelopment.databinding.FragmentFirstBinding


class FirstFragment : Fragment(R.layout.fragment_first) {
    private var viewBinding: FragmentFirstBinding? = null

    private var inputText: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentFirstBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        viewBinding?.apply {
            btnFirstFirstFragment.setOnClickListener {
                inputText = viewBinding?.etTextInput?.text.toString()
                (requireContext() as? MainActivity)?.navigate(
                    destination = SecondFragment.getInstance(param = inputText ?: ""),
                    destinationTag = SecondFragment.SECOND_TAG,
                    action = NavigationAction.REPLACE
                )
            }
            btnSecondFirstFragment.setOnClickListener {
                inputText = viewBinding?.etTextInput?.text.toString()
                (requireContext() as? MainActivity)?.navigate(
                    destination = SecondFragment.getInstance(param = inputText ?: "" ),
                    destinationTag = SecondFragment.SECOND_TAG,
                    action = NavigationAction.REPLACE
                )
                (requireContext() as? MainActivity)?.navigate(
                    destination = ThirdFragment.getInstance(param = inputText ?: "" ),
                    destinationTag = ThirdFragment.THIRD_TAG,
                    action = NavigationAction.REPLACE
                )
            }

            btnThirdFirstFragment.setOnClickListener {
                val dialog = BottomSheetFragment().apply {
                    isCancelable = true
                }
                dialog.show(childFragmentManager, BottomSheetFragment.DIALOG_FRAGMENT_TAG)
            }
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    fun updateEt(updateText: String) {
        viewBinding?.apply {
            etTextInput.setText(updateText)
        }
    }

    companion object {
        const val FIRST_TAG = "FIRST_TAG"
    }
}