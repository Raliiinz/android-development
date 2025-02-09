package ru.itis.androiddevelopment.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.data.db.converters.Converters
import ru.itis.androiddevelopment.data.db.entities.UserEntity
import ru.itis.androiddevelopment.databinding.FragmentRegistrationBinding
import ru.itis.androiddevelopment.di.ServiceLocator

class RegistrationFragment: Fragment(R.layout.fragment_registration) {

    private var viewBinding: FragmentRegistrationBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()
    private var photoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentRegistrationBinding.bind(view)

        viewBinding?.buttonRegister?.setOnClickListener {
            registerUser()
        }

        viewBinding?.imageViewAvatar?.setOnClickListener {
            givePermission()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    private fun registerUser() {
        val surname = viewBinding?.editTextSurname?.text.toString().trim()
        val name = viewBinding?.editTextName?.text.toString().trim()
        val email = viewBinding?.editTextEmail?.text.toString().trim()
        val password = viewBinding?.editTextPassword?.text.toString().trim()

        if (surname.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.fill_all_fields))
            return
        }

        if (!isEmailValid(email)) {
            showToast(getString(R.string.invalid_email))
            return
        }

        val photoByteArray = photoUri?.let { uri ->
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.let { bitmap ->
                    Converters().fromBitmap(bitmap)
                }
            }
        }

        lifecycleScope.launch {
            val existingUser = userRepository.getUserByEmail(email)
            if (existingUser != null) {
                showToast(getString(R.string.user_already_registered))
                return@launch
            }

            val user = UserEntity(
                email = email,
                name = name,
                surname = surname,
                password = password,
                photo = photoByteArray
            )

            userRepository.saveUserData(user)


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
        }

    }

    private fun givePermission() {
        if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            (requireActivity() as? MainActivity)?.permissionsHandler?.requestSinglePermission(
                permission = android.Manifest.permission.READ_EXTERNAL_STORAGE,
                onGranted = { openGallery() },
                onDenied = { showToast(getString(R.string.permission_denied)) }
            )
        }else {
            openGallery()
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    @SuppressLint("CheckResult")
    private fun loadImage(imageUri: Uri) {
        viewBinding?.imageViewAvatar?.let {
            Glide.with(this)
                .load(imageUri)
                .circleCrop()
                .into(it)
        }
    }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                photoUri = uri
                loadImage(uri)
            }
        }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(emailRegex.toRegex())
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REGISTRATION_TAG = "REGISTRATION_TAG"
    }
}