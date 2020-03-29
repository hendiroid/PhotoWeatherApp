package com.app.photoweatherapp.di

import android.content.Context
import android.content.res.Resources
import com.app.photoweatherapp.utils.Constants
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.app.photoweatherapp.BuildConfig
import com.google.gson.GsonBuilder
import com.app.photoweatherapp.utils.interceptors.CurlLoggingInterceptor
import com.app.photoweatherapp.utils.interceptors.HeadersInterceptor
import com.app.photoweatherapp.utils.interceptors.ResponseLoggingInterceptor
import com.app.photoweatherapp.utils.managers.InternetConnectionManager
import com.app.photoweatherapp.utils.managers.InternetConnectionManagerInterface
import com.app.photoweatherapp.utils.managers.SharedPreferencesManagerInterface
import com.ibtdi.musculi.utils.managers.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class, SharedPreferencesModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideInternetConnectionManager(context: Context): InternetConnectionManagerInterface {
        return InternetConnectionManager(context)
    }

    @Provides
    @Singleton
    fun provideApiRequestManager(resources: Resources): ApiRequestManagerInterface {
        return ApiRequestManager(resources)
    }

    @Provides
    @Singleton
    @Named("baseURL")
    fun provideBaseURL(): String {
        return Constants.baseURL
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferencesManager: SharedPreferencesManagerInterface): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(HeadersInterceptor(sharedPreferencesManager))

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder
                .addInterceptor(CurlLoggingInterceptor())
                .addInterceptor(ResponseLoggingInterceptor())
        }

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        @Named("baseURL") baseUrl: String,
        gson: Gson,
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()
    }
}
