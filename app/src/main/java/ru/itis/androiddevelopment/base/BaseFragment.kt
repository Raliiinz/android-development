package ru.itis.androiddevelopment.base

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    protected open var composeView: ComposeView? = null
}