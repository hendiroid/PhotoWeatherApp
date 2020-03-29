package com.app.photoweatherapp.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("text")
fun setTemp(textView: TextView, temp: Double) {
    textView.text = convertFromKelvinToCelsius(temp).toString()
}