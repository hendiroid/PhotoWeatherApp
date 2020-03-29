package com.app.photoweatherapp.utils

import com.app.photoweatherapp.models.Message

sealed class ResponseResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResponseResult<T>()
    data class Failure(val message: Message) : ResponseResult<Nothing>()
}