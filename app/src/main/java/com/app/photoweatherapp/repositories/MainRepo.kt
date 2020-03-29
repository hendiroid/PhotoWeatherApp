package com.app.photoweatherapp.repositories

import com.app.photoweatherapp.models.DataModel
import com.app.photoweatherapp.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface MainApis {

    @GET(Constants.weather)
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") appId: String
    ): Response<DataModel>
}

interface MainRepoInterface {
    suspend fun getWeatherData(city: String, appId: String): Response<DataModel>
}

class MainRepo @Inject constructor(retrofit: Retrofit) : MainRepoInterface {
    private val api = retrofit.create(MainApis::class.java)

    override suspend fun getWeatherData(city: String, appId: String): Response<DataModel> =
        api.getWeatherData(city, appId)

}