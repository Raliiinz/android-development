package ru.itis.androiddevelopment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import ru.itis.androiddevelopment.base.BaseActivity
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.databinding.ActivityMainBinding
import ru.itis.androiddevelopment.fragments.MainFragment
import ru.itis.androiddevelopment.utils.NotificationsHandler
import ru.itis.androiddevelopment.utils.PermissionsHandler


class MainActivity : BaseActivity(){
    override var mainContainerId = R.id.main_fragment_container

    private var viewBinding: ActivityMainBinding? = null
    var notificationsHandler: NotificationsHandler? = null
    var permissionsHandler: PermissionsHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(currentThemeResId)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        val isFromNotification = intent?.getBooleanExtra("isFromNotification", false) ?: false
        if (isFromNotification && savedInstanceState == null) {
            showToast(getString(R.string.launched_from_notification))
        }

        navigate(
            destination = MainFragment(),
            destinationTag = MainFragment.MAIN_FRAGMENT_TAG,
            action = NavigationAction.REPLACE,
            isAddToBackStack = false
        )


        permissionsHandler = PermissionsHandler(
            onSinglePermissionGranted = {
                showToast(getString(R.string.permission_notifications_granted))
            },
            onSinglePermissionDenied = {
                showToast(getString(R.string.permission_notifications_denied))
            }
        )
        permissionsHandler?.initContracts(this)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionsHandler?.requestSinglePermission(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (notificationsHandler == null) {
           notificationsHandler = NotificationsHandler(this.applicationContext)
        }
    }

    fun applyTheme(themeResId: Int) {
        currentThemeResId = themeResId
        recreate()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        viewBinding = null
        notificationsHandler = null
        permissionsHandler = null
        super.onDestroy()
    }

    companion object {
        var currentThemeResId: Int = R.style.Base_Theme_AndroidDevelopment
    }
}

