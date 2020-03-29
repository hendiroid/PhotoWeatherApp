package com.app.photoweatherapp.di

import com.app.photoweatherapp.ui.HistoryFragment
import com.app.photoweatherapp.ui.HomeFragment
import com.app.photoweatherapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [
            ApplicationModule::class,
            RepositoriesModule::class,
            ViewModelModule::class,
            SharedPreferencesModule::class
        ]
)
@Singleton
interface ApplicationComponent {

    fun inject(target: MainActivity)
    fun inject(target:HomeFragment)
    fun inject(target:HistoryFragment)

}