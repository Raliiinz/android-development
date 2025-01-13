package ru.itis.androiddevelopment.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.adapter.ColorsAdapter
import ru.itis.androiddevelopment.databinding.FragmentMainBinding
import ru.itis.androiddevelopment.model.NotificationData
import ru.itis.androiddevelopment.model.NotificationLevel
import ru.itis.androiddevelopment.model.NotificationType
import ru.itis.androiddevelopment.repository.ColorRepository
import ru.itis.androiddevelopment.repository.ColorRepositoryImpl
import ru.itis.androiddevelopment.utils.NotificationsHandler

class MainFragment: Fragment(R.layout.fragment_main) {
    private var viewBinding: FragmentMainBinding? = null
    private var notificationsHandler: NotificationsHandler? = null
    private var notificationCounter = 0
    private val colorRepository: ColorRepository = ColorRepositoryImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMainBinding.bind(view)
        if (notificationsHandler == null) {
            notificationsHandler = (requireActivity() as? MainActivity)?.notificationsHandler
        }

        initColors()
        initViews()
    }

    private fun initColors() {
        colorRepository.addColor(Color.RED)
        colorRepository.addColor(Color.GREEN)
        colorRepository.addColor(Color.YELLOW)
    }

    private fun initViews() {
        viewBinding?.apply {
            setupSpinner(spinnerImportance)

            btnShowNotification.setOnClickListener {
                showNotification()
            }

            circularImageView.setOnClickListener { showImageOptionsDialog() }
            btnDelete.setOnClickListener { clearImage() }

            colorsRecyclerView.adapter = ColorsAdapter(colorRepository.getAllColors()) { color ->
                applyTheme(getThemeForColor(color))
            }
            colorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
        return when (NotificationLevel.fromString(importance)) {
            NotificationLevel.MAX -> NotificationType.URGENT
            NotificationLevel.HIGH -> NotificationType.PRIVATE
            NotificationLevel.LOW -> NotificationType.LOW
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
                    0 -> loadImage()
                    1 -> (requireActivity() as? MainActivity)?.permissionsHandler?.requestSinglePermission(
                        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        onGranted = { openGallery() },
                        onDenied = { showToast(getString(R.string.permission_denied)) }
                    )
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

    private fun getThemeForColor(color: Int): Int {
        return when (color) {
            Color.RED -> R.style.RedTheme
            Color.GREEN -> R.style.GreenTheme
            Color.YELLOW -> R.style.YellowTheme
            else -> R.style.Base_Theme_AndroidDevelopment
        }
    }

    private fun toggleDropDown() {
        viewBinding?.apply {
            val isDropDownVisible = colorsRecyclerView.isVisible
            colorsRecyclerView.visibility = if (isDropDownVisible) View.GONE else View.VISIBLE
            dropDownBtn.setImageResource(
                if (isDropDownVisible) R.drawable.ic_baseline_keyboard_double_arrow_down_24
                else R.drawable.ic_baseline_keyboard_double_arrow_up_24
            )
        }
    }

    private fun applyTheme(themeResId: Int) {
        (requireActivity() as? MainActivity)?.applyTheme(themeResId)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
        notificationsHandler = null
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}