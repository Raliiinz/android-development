package ru.itis.androiddevelopment.fragments

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.data.db.converters.Converters
import ru.itis.androiddevelopment.data.db.entities.UserEntity
import ru.itis.androiddevelopment.databinding.FragmentProfileBinding
import ru.itis.androiddevelopment.di.ServiceLocator

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private var viewBinding: FragmentProfileBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()
    private var currentUserEmail: String? = null
    private var photoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentProfileBinding.bind(view)

        currentUserEmail = arguments?.getString(EMAIL_TEXT)
        if (currentUserEmail.isNullOrEmpty()) {
            showToast(getString(R.string.error_unknown_user))
            return
        }

        loadUserProfile()
        viewBinding?.imageViewProfile?.setOnClickListener {
            givePermission()
        }
        viewBinding?.btnRemovePhoto?.setOnClickListener {
            removePhoto()
        }
        viewBinding?.buttonDeleteAccount?.setOnClickListener {
            showDeleteAccountConfirmation()
        }
        viewBinding?.buttonSaveChanges?.setOnClickListener {
            saveUserProfile()
        }
    }


    private fun loadUserProfile() {
        lifecycleScope.launch {
            val user = currentUserEmail?.let { userRepository.getUserByEmail(it) }
            user?.let {
                viewBinding?.editTextName?.setText(it.name)
                viewBinding?.editTextSurname?.setText(it.surname)
                viewBinding?.editTextEmail?.setText(it.email)
                viewBinding?.editTextPassword?.setText(it.password)

                it.photo?.let { byteArray ->
                    Glide.with(this@ProfileFragment)
                        .asBitmap()
                        .load(byteArray)
                        .circleCrop()
                        .into(viewBinding?.imageViewProfile!!)
                    viewBinding?.btnRemovePhoto?.visibility = View.VISIBLE
                } ?: run {
                    Glide.with(this@ProfileFragment)
                        .load(R.drawable.ic_person_photo)
                        .circleCrop()
                        .into(viewBinding?.imageViewProfile!!)
                    viewBinding?.btnRemovePhoto?.visibility = View.GONE
                }
            }
        }
    }


    private fun saveUserProfile() {
        val name = viewBinding?.editTextName?.text.toString().trim()
        val surname = viewBinding?.editTextSurname?.text.toString().trim()
        val password = viewBinding?.editTextPassword?.text.toString().trim()

        if (name.isEmpty() || surname.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.fill_all_fields))
            return
        }

        lifecycleScope.launch {
            currentUserEmail?.let {
                val updatedUser = UserEntity(
                    email = it,
                    name = name,
                    surname = surname,
                    password = password,
                    photo = convertPhotoToByteArray()
                )
                userRepository.updateUser(updatedUser)
                showToast(getString(R.string.account_update))
            }
        }
    }

    private fun convertPhotoToByteArray(): ByteArray? {
        return photoUri?.let { uri ->
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.let { bitmap ->
                    Converters().fromBitmap(bitmap)
                }
            }
        }
    }


    private fun showDeleteAccountConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.alert_title_delete))
            .setMessage(getString(R.string.alert_message_delete))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                deleteAccount()
            }
            .setNegativeButton(getString(R.string.alert_cancellation), null)
            .create()
            .show()
    }


    private fun deleteAccount() {
        lifecycleScope.launch {
            currentUserEmail?.let {
                userRepository.deleteUserByEmail(it)
                (requireActivity() as? MainActivity)?.saveLoginState(requireContext(), isLoggedIn = false)
                (requireActivity() as? MainActivity)?.hideBottomNavigation()
                (requireActivity() as? MainActivity)?.navigate(
                    destination = AuthorizationFragment(),
                    destinationTag = AuthorizationFragment.AUTHORIZATION_TAG,
                    action = NavigationAction.REPLACE,
                    isAddToBackStack = false
                )
            }
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

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                photoUri = uri
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(viewBinding?.imageViewProfile!!)
                viewBinding?.btnRemovePhoto?.visibility = View.VISIBLE
            }
        }

    private fun removePhoto() {
        photoUri = null
        viewBinding?.imageViewProfile?.setImageResource(R.drawable.ic_person_photo)
        viewBinding?.btnRemovePhoto?.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val PROFILE_TAG = "PROFILE_TAG"
        private const val EMAIL_TEXT = "EMAIL_TEXT"

        fun getInstance(email: String): ProfileFragment {
            return ProfileFragment().apply {
                arguments = bundleOf(EMAIL_TEXT to email)
            }
        }
    }
}