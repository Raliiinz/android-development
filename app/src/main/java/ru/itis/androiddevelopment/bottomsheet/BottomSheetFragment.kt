package ru.itis.androiddevelopment.bottomsheet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.databinding.DialogBottomSheetBinding
import ru.itis.androiddevelopment.fragment.FirstFragment

class BottomSheetFragment : BottomSheetDialogFragment(R.layout.dialog_bottom_sheet) {
    private var viewBinding: DialogBottomSheetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = DialogBottomSheetBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        viewBinding?.btnBottomSheet?.isEnabled = false

        viewBinding?.etTextInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewBinding?.btnBottomSheet?.isEnabled = !s.isNullOrEmpty()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        viewBinding?.btnBottomSheet?.setOnClickListener {
            viewBinding?.etTextInput?.text?.toString()
                ?.let { it1 -> (parentFragment as? FirstFragment)?.updateEt(it1) }
        }


//            viewBinding?.btnBottomSheet?.setOnClickListener {
//            (requireContext() as? MainActivity)?.navigate(
//                destination = FirstFragment.getInstance(viewBinding?.etTextInput?.text.toString()),
//                destinationTag = FirstFragment.FIRST_TAG,
//                action = NavigationAction.REPLACE
//            )
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"
    }
}