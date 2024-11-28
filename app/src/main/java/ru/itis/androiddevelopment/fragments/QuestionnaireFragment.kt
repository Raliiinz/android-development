package ru.itis.androiddevelopment.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.adapter.QuestionnaireAdapter
import ru.itis.androiddevelopment.databinding.FragmentQuestionnaireBinding
import ru.itis.androiddevelopment.repository.QuestionsRepository

class QuestionnaireFragment: Fragment(R.layout.fragment_questionnaire) {
    private var viewBinding: FragmentQuestionnaireBinding? = null
    private var vpAdapter: QuestionnaireAdapter? = null
    private val questions = QuestionsRepository.getQuestions()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentQuestionnaireBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        vpAdapter = QuestionnaireAdapter(
            manager = parentFragmentManager,
            lifecycle = this.lifecycle,
            questionsAnswers = questions
        )

        viewBinding?.apply {
            contentVp.apply {
                adapter = vpAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        updateUI(position)
                    }
                })
            }

            firstBtn.setOnClickListener {
                val currentItem = contentVp.currentItem
                if (currentItem > 0) {
                    contentVp.currentItem = currentItem - 1
                }
            }

            secondBtn.setOnClickListener {
                val currentItem = contentVp.currentItem
                if (currentItem < questions.size - 1) {
                    contentVp.currentItem = currentItem + 1
                } else {
                    if (allQuestionsAnswered()) {
                        showToast(requireContext().getString(R.string.results_saved))
                    }
                }
            }
        }
    }

    private fun updateUI(currentPosition: Int) {
        viewBinding?.apply {
            pageTv.text = "Вопрос №${currentPosition + 1} из ${questions.size}"

            firstBtn.apply {
                isEnabled = currentPosition > 0
                if (isEnabled) {
                    setBackgroundColor(getButtonColor(R.color.purple))
                }else{
                    setBackgroundColor(getButtonColor(R.color.LightGray))
                }
            }

            secondBtn.apply {
                if (currentPosition == questions.size - 1) {
                    text = context.getString(R.string.complete)
                    setBackgroundColor(getButtonColor(R.color.green))
                } else {
                    text = context.getString(R.string.next_question)
                    setBackgroundColor(getButtonColor(R.color.purple))
                }
            }
        }
    }

    private fun getButtonColor(colorToButton : Int) =
        ContextCompat.getColor(requireContext(), colorToButton)


    private fun allQuestionsAnswered(): Boolean {
        return questions.all { question ->
            question.answers.any { it.isChecked }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val QUESTIONNAIRE_TAG = "QUESTIONNAIRE_TAG"
    }
}