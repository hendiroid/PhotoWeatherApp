package com.app.photoweatherapp.app

import android.app.Application
import com.app.photoweatherapp.di.ApplicationComponent
import com.app.photoweatherapp.di.ApplicationModule
import com.app.photoweatherapp.di.DaggerApplicationComponent

class UserApplication: Application() {

    lateinit var component: ApplicationComponent
    private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }
}