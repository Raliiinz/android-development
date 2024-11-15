package ru.itis.androiddevelopment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.androiddevelopment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){
    val mainContainerId = R.id.main_fragment_container

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)


    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }
}

