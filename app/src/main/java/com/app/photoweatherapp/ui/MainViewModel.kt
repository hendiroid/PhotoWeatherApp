package com.app.photoweatherapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.photoweatherapp.models.DataModel
import com.app.photoweatherapp.models.Message
import com.app.photoweatherapp.repositories.MainRepoInterface
import com.app.photoweatherapp.utils.Constants
import com.app.photoweatherapp.utils.ObservableString
import com.app.photoweatherapp.utils.ViewState
import com.app.photoweatherapp.utils.managers.InternetConnectionManagerInterface
import com.ibtdi.musculi.utils.managers.ApiRequestManagerInterface
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRepo: MainRepoInterface,
    private val internetConnectionManager: InternetConnectionManagerInterface,
    private val apiRequestManager: ApiRequestManagerInterface
) : ViewModel() {

    val weatherDataViewState = MutableLiveData<ViewState<DataModel>>()
    val date = ObservableString("")

    fun getWeatherData(city: String) {
        if (internetConnectionManager.isConnectedToInternet) {
            weatherDataViewState.value = ViewState.Loading
            apiRequestManager.execute(
                request = {
                    mainRepo.getWeatherData(city, Constants.appId)
                },
                onSuccess = { data, headers ->
                    weatherDataViewState.value = ViewState.Success(data)
                    date.set(headers.get("Date").toString())
                },
                onFailure = {
                    weatherDataViewState.value = ViewState.Error(it)
                }
            )
        } else {
            weatherDataViewState.value = ViewState.Error(Message("No internet connection"))
        }
    }
}