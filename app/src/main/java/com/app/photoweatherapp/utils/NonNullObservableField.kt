package com.app.photoweatherapp.utils

import androidx.databinding.Observable
import androidx.databinding.ObservableField


typealias ObservableString = NonNullObservableField<String>
typealias ObservableBoolean = NonNullObservableField<Boolean>
typealias ObservableDouble = NonNullObservableField<Double>
typealias ObservableLong = NonNullObservableField<Long>
typealias ObservableInt = NonNullObservableField<Int>

class NonNullObservableField<T : Any>(value: T, vararg dependencies: Observable) : ObservableField<T>(*dependencies) {
    init {
        set(value)
    }

    override fun get(): T = super.get()!!

    @Suppress("RedundantOverride")
    override fun set(value: T) = super.set(value)
}