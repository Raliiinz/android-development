package ru.itis.androiddevelopment

import android.os.Bundle
import ru.itis.androiddevelopment.base.BaseActivity
import ru.itis.androiddevelopment.base.NavigationAction
import ru.itis.androiddevelopment.databinding.ActivityMainBinding
import ru.itis.androiddevelopment.fragment.MainFragment

class MainActivity : BaseActivity(){
    override val mainContainerId = R.id.main_fragment_container

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        navigate(
            destination = MainFragment(),
            destinationTag = MainFragment.MAIN_FRAGMENT_TAG,
            action = NavigationAction.ADD,
            isAddToBackStack = false
        )
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }
}

