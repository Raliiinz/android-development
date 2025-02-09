package ru.itis.androiddevelopment

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.itis.androiddevelopment.base.BaseActivity
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.databinding.ActivityMainBinding
import ru.itis.androiddevelopment.fragments.AuthorizationFragment
import ru.itis.androiddevelopment.fragments.ProfileFragment
import ru.itis.androiddevelopment.fragments.WishesFragment
import ru.itis.androiddevelopment.util.Constants
import ru.itis.androiddevelopment.util.PermissionsHandler


class MainActivity : BaseActivity() {

    override val mainContainerId = R.id.main_fragment_container
    private var viewBinding: ActivityMainBinding? = null
    var permissionsHandler: PermissionsHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        permissionsHandler = PermissionsHandler(
            onSinglePermissionGranted = {},
            onSinglePermissionDenied = {}
        )
        permissionsHandler?.initContracts(this)

        viewBinding?.mainBottomNavigation?.setOnItemReselectedListener {}


        hideBottomNavigation()

        if (isUserLoggedIn()) {
            val userEmail = getLoggedInUserEmail()
            navigateToWishesFragment(userEmail)
        } else {
            navigateToAuthorizationFragment()
        }
    }


    override fun onDestroy() {
        viewBinding = null
        permissionsHandler = null
        super.onDestroy()
    }


    fun showBottomNavigation() {
        viewBinding?.mainBottomNavigation?.visibility = View.VISIBLE
    }


    fun hideBottomNavigation() {
        viewBinding?.mainBottomNavigation?.visibility = View.GONE
    }

    fun saveLoginState(context: Context, isLoggedIn: Boolean, email: String? = null) {
        val sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(Constants.KEY_IS_LOGGED_IN, isLoggedIn)
            putString(Constants.KEY_USER_EMAIL, email)
            apply()
        }
    }


    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
    }


    private fun getLoggedInUserEmail(): String? {
        val sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.KEY_USER_EMAIL, null)
    }


    private fun navigateToAuthorizationFragment() {
        hideBottomNavigation()
        navigate(
            destination = AuthorizationFragment(),
            destinationTag = AuthorizationFragment.AUTHORIZATION_TAG,
            action = NavigationAction.REPLACE,
            isAddToBackStack = false
        )
    }


    private fun navigateToWishesFragment(email: String?) {
        if (!email.isNullOrEmpty()) {
            showBottomNavigation()
            setupBottomNavigation()
            navigate(
                destination = WishesFragment.getInstance(param = email),
                destinationTag = WishesFragment.WISHES_TAG,
                action = NavigationAction.REPLACE,
                isAddToBackStack = false
            )
        } else {
            navigateToAuthorizationFragment()
        }
    }


    private fun setupBottomNavigation() {
        val bottomNavigationView = viewBinding?.mainBottomNavigation
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main_tab -> {
                    val userEmail = getLoggedInUserEmail()
                    if (!userEmail.isNullOrEmpty()) {
                        navigate(
                            destination = WishesFragment.getInstance(param = userEmail),
                            destinationTag = WishesFragment.WISHES_TAG,
                            action = NavigationAction.REPLACE,
                            isAddToBackStack = false
                        )
                    }
                    true
                }
                R.id.menu_profile_tab -> {
                    val userEmail = getLoggedInUserEmail()
                    if (!userEmail.isNullOrEmpty()) {
                        navigate(
                            destination = ProfileFragment.getInstance(userEmail),
                            destinationTag = ProfileFragment.PROFILE_TAG,
                            action = NavigationAction.REPLACE,
                            isAddToBackStack = false
                        )
                    }
                    true
                }
                else -> false
            }
        }
    }
}

