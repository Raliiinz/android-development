package ru.itis.androiddevelopment.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.data.db.entities.WishEntity
import ru.itis.androiddevelopment.databinding.FragmentWishesBinding
import ru.itis.androiddevelopment.di.ServiceLocator
import ru.itis.androiddevelopment.recyclerView.WishAdapter
import ru.itis.androiddevelopment.util.SortType

class WishesFragment: Fragment(R.layout.fragment_wishes) {

    private var viewBinding: FragmentWishesBinding? = null
    private var wishRepository = ServiceLocator.getWishRepository()
    private var rvAdapter: WishAdapter? = null
    private var currentUserEmail: String? = null
    private var currentSortType: SortType = SortType.DEFAULT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentWishesBinding.bind(view)

        setupRecyclerView()
        currentUserEmail = arguments?.getString(EMAIL_TEXT)

        if (currentUserEmail.isNullOrEmpty()) {
            handleUnknownUser()
            return
        }

        setupSpinner()
        loadWishes()
        setupLogoutButton()
        viewBinding?.fabAddWish?.setOnClickListener {
            showAddWishBottomSheet()
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = WishAdapter { wish -> onDeleteWish(wish) }
        viewBinding?.recyclerViewWishes?.adapter = rvAdapter
    }

    private fun handleUnknownUser() {
        showToast(getString(R.string.error_unknown_user))
        navigateToAuthorization()
    }

    private fun navigateToAuthorization() {
        (requireActivity() as? MainActivity)?.navigate(
            destination = AuthorizationFragment(),
            destinationTag = AuthorizationFragment.AUTHORIZATION_TAG,
            action = NavigationAction.REPLACE,
            isAddToBackStack = true
        )
    }

    private fun setupLogoutButton() {
        viewBinding?.buttonLogout?.setOnClickListener {
            (requireActivity() as? MainActivity)?.saveLoginState(requireContext(), isLoggedIn = false)
            navigateToAuthorization()
            (requireActivity() as? MainActivity)?.hideBottomNavigation()
        }
    }

    private fun setupSpinner() {
        val spinner = viewBinding?.spinnerFilter ?: return
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentSortType = when (position) {
                    1 -> SortType.PRICE_ASC
                    2 -> SortType.PRICE_DESC
                    else -> SortType.DEFAULT
                }
                loadWishes()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                currentSortType = SortType.DEFAULT
            }
        }
    }


    private fun loadWishes() {
        lifecycleScope.launch {
            val wishes = currentUserEmail?.let { wishRepository.getWishesByEmail(it) }
            val sortedWishes = when (currentSortType) {
                SortType.PRICE_ASC -> wishes?.sortedBy { it.price }
                SortType.PRICE_DESC -> wishes?.sortedByDescending { it.price }
                SortType.DEFAULT -> wishes
            }
            if (sortedWishes != null) {
                rvAdapter?.updateDate(sortedWishes.toMutableList())
            }
        }
    }


    private fun onDeleteWish(wish: WishEntity) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.alert_message))
            .setPositiveButton(getString(R.string.delete)) { dialog, id ->
                lifecycleScope.launch {
                    wishRepository.deleteWish(wish)
                    val position = rvAdapter?.removeItem(wish)
                    position?.let {
                        rvAdapter?.notifyItemRemoved(it)
                    }
                }
            }
            .setNegativeButton(getString(R.string.alert_cancellation)) { dialog, id -> }
        builder.create().show()
    }


    private fun showAddWishBottomSheet() {
        if (currentUserEmail.isNullOrEmpty()) {
            showToast(getString(R.string.error_email_not_found))
            return
        }

        val bottomSheet = AddBottomSheetFragment.getInstance(
            email = currentUserEmail!!,
            onWishAdded = { wish ->
                lifecycleScope.launch {
                    wishRepository.saveWish(wish)
                    loadWishes()
                }
            }
        )
        bottomSheet.show(childFragmentManager, AddBottomSheetFragment.BOTTOM_SHEET_TAG)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val WISHES_TAG = "WISHES_TAG"
        private const val EMAIL_TEXT = "EMAIL_TEXT"

        fun getInstance(
            param: String
        ):WishesFragment {
            return WishesFragment().apply {
                arguments = bundleOf(EMAIL_TEXT to param)
            }
        }
    }
}