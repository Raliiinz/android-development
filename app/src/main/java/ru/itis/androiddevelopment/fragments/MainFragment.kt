package ru.itis.androiddevelopment.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.databinding.FragmentMainBinding
import ru.itis.androiddevelopment.model.NotificationData
import ru.itis.androiddevelopment.model.NotificationType
import ru.itis.androiddevelopment.utils.NotificationsHandler
import ru.itis.androiddevelopment.utils.PermissionsHandler

class MainFragment: Fragment(R.layout.fragment_main) {
    private var viewBinding: FragmentMainBinding? = null
    private var notificationsHandler: NotificationsHandler? = null
    private var notificationCounter = 0
    private var permissionsHandler: PermissionsHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().setTheme(currentThemeResId)
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMainBinding.bind(view)
        if (notificationsHandler == null) {
            notificationsHandler = (requireActivity() as? MainActivity)?.notificationsHandler
        }

        // Инициализация PermissionsHandler
        permissionsHandler = PermissionsHandler(
            onSinglePermissionGranted = { openGallery() },
            onSinglePermissionDenied = { showToast(getString(R.string.permission_denied))}
        ).apply {
            initContracts(requireActivity() as MainActivity)
        }

        initViews()
    }

    private fun initViews() {
        viewBinding?.apply {
            setupSpinner(spinnerImportance)

            btnShowNotification.setOnClickListener {
                showNotification()
            }

            circularImageView.setOnClickListener { showImageOptionsDialog() }
            btnDelete.setOnClickListener { clearImage() }

            redColor.setOnClickListener { applyTheme(R.style.RedTheme) }
            greenColor.setOnClickListener { applyTheme(R.style.GreenTheme) }
            yellowColor.setOnClickListener { applyTheme(R.style.YellowTheme) }
            dropDownBtn.setOnClickListener { toggleDropDown() }
            btnResetTheColor.setOnClickListener { applyTheme(R.style.Base_Theme_AndroidDevelopment) }
        }
    }


    private fun setupSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.importance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }


    private fun showNotification() {
        val title = viewBinding?.etTitleInput?.text.toString()
        val message = viewBinding?.etTextInput?.text.toString()
        val importance = viewBinding?.spinnerImportance?.selectedItem?.toString().orEmpty()

        when {
            title.isEmpty() -> showToast(getString(R.string.notification_title_empty))
            message.isEmpty() -> showToast(getString(R.string.notification_text_empty))
            else -> {
                notificationsHandler?.showNotification(
                    NotificationData(
                        id = ++notificationCounter,
                        title = title,
                        message = message,
                        notificationType = getNotificationType(importance)
                    )
                )
            }
        }
    }

    private fun getNotificationType(importance: String): NotificationType {
        return when (importance) {
            "Max" -> NotificationType.URGENT
            "High" -> NotificationType.PRIVATE
            "Low" -> NotificationType.LOW
            else -> NotificationType.DEFAULT
        }
    }


    private fun showImageOptionsDialog() {
        val options = arrayOf(
            getString(R.string.load_default),
            getString(R.string.choose_from_gallery)
        )

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_action))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> loadImage() // Загрузка из интернета
                    1 -> permissionsHandler?.requestSinglePermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    // Выбор из галереи
                }
            }
            .show()
    }

    // Открытие галереи
    @SuppressLint("IntentReset")
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    // Callback для получения результата выбора изображения
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val selectedImageUri: Uri? = result.data!!.data
                if (selectedImageUri != null) {
                    loadImage(selectedImageUri)
                }
            }
        }

    // Загрузка изображения в ImageView
    private fun loadImage(imageUri: Uri? = null) {
        val imageUrl = imageUri?.toString()
            ?: "https://www.baltana.com/files/wallpapers-13/Minions-Wallpapers-Desktop-34337.jpg"

        viewBinding?.circularImageView?.let {
            Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .into(it)
        }

        viewBinding?.btnDelete?.isVisible = true
    }

//    private fun loadDefaultImage() {
//        val imageUrl = "https://www.baltana.com/files/wallpapers-13/Minions-Wallpapers-Desktop-34337.jpg"
//        viewBinding?.circularImageView?.let {
//            Glide.with(this)
//                .load(imageUrl)
//                .apply(RequestOptions().circleCrop())
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable,
//                        model: Any,
//                        target: Target<Drawable>?,
//                        dataSource: DataSource,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        viewBinding?.btnDelete?.isVisible = true
//                        return false
//                    }
//
//                })
//                .into(it)
//        }
//    }


    private fun clearImage() {
        viewBinding?.circularImageView?.setImageDrawable(null)
        viewBinding?.btnDelete?.isVisible = false
    }

    private fun toggleDropDown() {
        viewBinding?.apply {
            val isDropDownVisible = colorContainer.isVisible
            colorContainer.visibility = if (isDropDownVisible) View.GONE else View.VISIBLE
            dropDownBtn.setImageResource(
                if (isDropDownVisible) R.drawable.ic_baseline_keyboard_double_arrow_down_24
                else R.drawable.ic_baseline_keyboard_double_arrow_up_24
            )
        }
    }

    private fun applyTheme(themeResId: Int) {
        currentThemeResId = themeResId
        requireActivity().recreate()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
        notificationsHandler = null
        permissionsHandler = null
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        private var currentThemeResId: Int = R.style.Base_Theme_AndroidDevelopment
    }
}