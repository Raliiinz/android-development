package ru.itis.androiddevelopment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.itis.androiddevelopment.fragments.AnswerFragment
import ru.itis.androiddevelopment.model.Question

class QuestionnaireAdapter(
    private val questionsAnswers:MutableList<Question>,
    manager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle){

    override fun getItemCount(): Int {
        return questionsAnswers.size
    }

    override fun createFragment(position: Int): Fragment {
        return AnswerFragment.newInstance(questionsAnswers[position].id)
    }
}