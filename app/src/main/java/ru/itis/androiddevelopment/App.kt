package ru.itis.androiddevelopment

import android.app.Application
import ru.itis.androiddevelopment.di.ServiceLocator

class App : Application() {

    private val serviceLocator = ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator.initDataLayerDependencies(ctx = this)
    }
}