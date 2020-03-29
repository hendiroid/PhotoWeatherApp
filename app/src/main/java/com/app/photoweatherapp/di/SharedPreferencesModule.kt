package com.app.photoweatherapp.di

import com.app.photoweatherapp.utils.managers.SharedPreferencesManager
import com.app.photoweatherapp.utils.managers.SharedPreferencesManagerInterface
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
abstract class SharedPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindSharedPreferences(sharedPreferencesManager: SharedPreferencesManager): SharedPreferencesManagerInterface
}