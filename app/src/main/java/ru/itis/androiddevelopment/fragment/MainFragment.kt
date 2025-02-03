package ru.itis.androiddevelopment.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.BaseFragment
import ru.itis.androiddevelopment.databinding.FragmentComposeBinding
import ru.itis.androiddevelopment.fragment.ui.BaseComposeScreen
class MainFragment : BaseFragment(R.layout.fragment_compose) {
    private var viewBinding: FragmentComposeBinding? = null

    private var permissionDeniedCount = 0
    private var isDialogDismissedPermanently by mutableStateOf(false)

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                permissionDeniedCount++
                if (permissionDeniedCount >= 2 && !isDialogDismissedPermanently) {
                    showDialog = true
                }
            } else {
                permissionDeniedCount = 0
            }
        }

    private var showDialog by mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentComposeBinding.bind(view)
        viewBinding?.composeContainerId?.setContent {
            BaseComposeScreen(
                onRequestPermission = {
                    checkAndRequestPermission()
                },
                showDialog = showDialog,
                isDialogDismissedPermanently = isDialogDismissedPermanently,
                onDismissDialog = {
                    isDialogDismissedPermanently = true
                    showDialog = false
                },
                onOpenSettings = { openAppSettings() }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
        composeView = null
    }

    private fun checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Разрешение уже предоставлено
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                    showDialog = true
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(intent)
    }
    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}