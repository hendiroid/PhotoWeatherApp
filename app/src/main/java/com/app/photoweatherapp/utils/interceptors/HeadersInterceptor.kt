package com.app.photoweatherapp.utils.interceptors

import com.app.photoweatherapp.utils.managers.SharedPreferencesManagerInterface
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor(private val sharedPreferencesManager: SharedPreferencesManagerInterface) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        return chain.proceed(request.build())
    }
}