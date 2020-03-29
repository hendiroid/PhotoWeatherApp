package com.app.photoweatherapp.models

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message") var errorMessage: String = "",
    var code: Int = 0
)