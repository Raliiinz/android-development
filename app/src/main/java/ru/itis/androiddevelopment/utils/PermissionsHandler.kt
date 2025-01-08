package ru.itis.androiddevelopment.utils

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionsHandler (
    private val onSinglePermissionGranted: (() -> Unit)? = null,
    private val onSinglePermissionDenied: (() -> Unit)? = null
) {

    private var activity: AppCompatActivity? = null

    private var singlePermissionResult: ActivityResultLauncher<String>? = null

    fun initContracts(activity: AppCompatActivity) {
        if (this.activity == null) {
            this.activity = activity
        }

        if (singlePermissionResult == null) {
            singlePermissionResult =
                this.activity?.registerForActivityResult(ActivityResultContracts. RequestPermission()) { isGranted ->
                    if (isGranted) {
                        onSinglePermissionGranted?.invoke()
                    } else {
                        onSinglePermissionDenied?.invoke()
                    }
                }
        }
    }

    fun requestSinglePermission(permission: String) {
        singlePermissionResult?.launch(permission)
    }
}