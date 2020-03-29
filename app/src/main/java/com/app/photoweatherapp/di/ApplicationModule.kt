package com.app.photoweatherapp.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.app.photoweatherapp.utils.managers.RuntimePermissionsManager
import com.app.photoweatherapp.utils.managers.RuntimePermissionsManagerInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.util.DisplayMetrics
import java.util.*

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideResources(): Resources {
        val conf = application.resources.configuration
        conf.locale = Locale(Locale.getDefault().language)
        val metrics = DisplayMetrics()
        return Resources(application.assets, metrics, conf)
    }

//    @Provides
//    @Singleton
//    fun provideRuntimePermissionManager(): RuntimePermissionsManagerInterface {
//        return RuntimePermissionsManager(1)
//    }
}