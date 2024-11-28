package ru.itis.androiddevelopment.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.adapter.AnswerAdapter
import ru.itis.androiddevelopment.databinding.FragmentAnswerBinding
import ru.itis.androiddevelopment.decorators.SimpleDecorator
import ru.itis.androiddevelopment.utils.getValueInDp
import ru.itis.androiddevelopment.repository.QuestionsRepository

class AnswerFragment: Fragment(R.layout.fragment_answer) {
    private var viewBinding: FragmentAnswerBinding? = null
    private var rvAdapter: AnswerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAnswerBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        viewBinding?.apply {
//            val question = arguments?.getParcelableSafe<Question>(ARG_QUESTION)
            val questionId = arguments?.getInt(ARG_QUESTION) ?: return

            rvAdapter = QuestionsRepository.getAnswersByQuestionId(questionId)?.let {
                AnswerAdapter(
                    items = it,
                    onItemChecked = { position -> updateAns(position) },
                    onRootClicked = { position -> updateAns(position) },
                    checkedColor = getCheckedColor(R.color.green),
                    uncheckedColor = getCheckedColor(R.color.white)
                )
            }
            answerRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            answerRv.adapter = rvAdapter
            answerRv.addItemDecoration(
                SimpleDecorator(
                    marginValue = getValueInDp(value = 8f, requireContext()).toInt()
                )
            )

            titleTv.text = QuestionsRepository.getQuestionTextById(questionId)
        }

    }

    private fun updateAns(position: Int) {
        rvAdapter?.apply {
            val pointedPos = items.indexOfFirst { it.isChecked }
            if (pointedPos != -1) {
                items[pointedPos].isChecked = false
                notifyItemChanged(pointedPos, false)
            }
            items[position].isChecked = true
            notifyItemChanged(position, true)
        }
    }

    private fun getCheckedColor(colorChecked : Int) =
        ContextCompat.getColor(requireContext(), colorChecked)

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        private const val ARG_QUESTION = "ARG_QUESTION"
        fun newInstance(id: Int) = AnswerFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_QUESTION, id)
            }
        }

//        fun newInstance(question: Question) = AnswerFragment().apply {
//            arguments = Bundle().apply {
//                putParcelable(ARG_QUESTION, question)
//            }
//        }
    }
}