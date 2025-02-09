package ru.itis.androiddevelopment.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.databinding.FragmentAuthorizationBinding
import ru.itis.androiddevelopment.di.ServiceLocator

class AuthorizationFragment: Fragment(R.layout.fragment_authorization) {

    private var viewBinding: FragmentAuthorizationBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAuthorizationBinding.bind(view)

        viewBinding?.buttonLogin?.setOnClickListener {
            login()
        }

        viewBinding?.buttonRegister?.setOnClickListener {
            (requireActivity() as? MainActivity)?.navigate(
                destination = RegistrationFragment(),
                destinationTag = RegistrationFragment.REGISTRATION_TAG,
                action = NavigationAction.REPLACE,
                isAddToBackStack = true)
        }
    }

    private fun login() {
        val email = viewBinding?.editTextEmail?.text.toString().trim()
        val password = viewBinding?.editTextPassword?.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.fill_all_fields))
            return
        }

        lifecycleScope.launch {
            val user = userRepository.authenticateUser(email, password)
            if (user != null) {
                (requireActivity() as? MainActivity)?.saveLoginState(
                    requireContext(),
                    isLoggedIn = true,
                    email = email
                )
                (requireActivity() as? MainActivity)?.showBottomNavigation()
                (requireActivity() as? MainActivity)?.navigate(
                    destination = WishesFragment.getInstance(param = email),
                    destinationTag = WishesFragment.WISHES_TAG,
                    action = NavigationAction.REPLACE,
                    isAddToBackStack = true
                )
            } else {
                showToast(getString(R.string.incorrect_email_or_password))
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val AUTHORIZATION_TAG = "AUTHORIZATION_TAG"
    }
}