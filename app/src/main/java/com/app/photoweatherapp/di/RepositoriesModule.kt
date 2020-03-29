package com.app.photoweatherapp.di

import com.app.photoweatherapp.repositories.MainRepo
import com.app.photoweatherapp.repositories.MainRepoInterface
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindMainRepo(repo: MainRepo): MainRepoInterface

}