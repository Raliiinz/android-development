package ru.itis.androiddevelopment.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.data.db.converters.Converters
import ru.itis.androiddevelopment.data.db.entities.WishEntity
import ru.itis.androiddevelopment.databinding.DialogBottomSheetBinding

class AddBottomSheetFragment(): BottomSheetDialogFragment(R.layout.dialog_bottom_sheet) {

    private var viewBinding: DialogBottomSheetBinding? = null
    private var photoUri: Uri? = null
    private var onWishAdded: ((WishEntity) -> Unit)? = null
    private val userEmail: String?
        get() = arguments?.getString(EMAIL_TEXT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = DialogBottomSheetBinding.bind(view)

        viewBinding?.buttonSelectPhoto?.setOnClickListener {
            givePermission()
        }

        viewBinding?.buttonSaveWish?.setOnClickListener {
            saveWish()
        }
    }

    private fun saveWish() {
        val wishName = viewBinding?.editTextWishName?.text.toString()
        val priceText = viewBinding?.editTextWishPrice?.text.toString().trim()
        val price = if (priceText.isEmpty()) 0.0 else priceText.toDouble()

        if (wishName.isEmpty()) {
            viewBinding?.editTextWishName?.error = getString(R.string.enter_the_name_wish)
            return
        }

        val photoByteArray = photoUri?.let { uri ->
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.let { bitmap ->
                    Converters().fromBitmap(bitmap)
                }
            }
        }

        val wish = WishEntity(
            userEmail = userEmail ?: "",
            wishName = wishName,
            price = price,
            photo = photoByteArray
        )
        onWishAdded?.invoke(wish)
        dismiss()
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
        viewBinding?.imageViewWishPhoto?.let {
            Glide.with(this)
                .load(imageUri)
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val BOTTOM_SHEET_TAG = "BOTTOM_SHEET_TAG"

        private const val EMAIL_TEXT = "EMAIL_TEXT"

        fun getInstance(email: String, onWishAdded: (WishEntity) -> Unit): AddBottomSheetFragment {
            return AddBottomSheetFragment().apply {
                arguments = bundleOf(EMAIL_TEXT to email)
                this.onWishAdded = onWishAdded
            }
        }
    }
}