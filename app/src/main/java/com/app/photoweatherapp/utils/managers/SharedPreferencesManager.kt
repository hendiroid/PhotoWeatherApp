package com.app.photoweatherapp.utils.managers

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import kotlin.reflect.KProperty

interface SharedPreferencesManagerInterface {
    fun <T> put(key: String, value: T)
    fun <T> get(key: String, defaultValue: T): T
    fun clear()
}

@Suppress("UNCHECKED_CAST")
class SharedPreferencesManager @Inject constructor(context: Context) :
    SharedPreferencesManagerInterface {

    private var sharedPreferences: SharedPreferences? =
        context.getSharedPreferences("preferencesData", Context.MODE_PRIVATE)

    private val editor = sharedPreferences?.edit()

    override fun <T> put(key: String, value: T) {
        when (value) {
            is String -> editor?.putString(key, value)?.apply()
            is Int -> editor?.putInt(key, value)?.apply()
            is Float -> editor?.putFloat(key, value)?.apply()
            is Long -> editor?.putLong(key, value)?.apply()
            is Boolean -> editor?.putBoolean(key, value)?.apply()
            else -> throw IllegalArgumentException("Only primitive data types can be stored in SharedPreferences")
        }
    }

    override fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences?.getString(key, defaultValue as String) as T
            is Int -> sharedPreferences?.getInt(key, defaultValue as Int) as T
            is Float -> sharedPreferences?.getFloat(key, defaultValue as Float) as T
            is Long -> sharedPreferences?.getLong(key, defaultValue as Long) as T
            is Boolean -> sharedPreferences?.getBoolean(key, defaultValue as Boolean) as T
            else -> throw IllegalArgumentException("Only primitive data types can be stored in SharedPreferences")
        }
    }

    override fun clear() {
        editor?.clear()?.apply()
    }
}

class SharedPreferencesDelegation<T>(
    private val sharedPreferencesManager: SharedPreferencesManagerInterface,
    private val key: String,
    private val defaultValue: T
) {

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        sharedPreferencesManager.put(key, value)
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return sharedPreferencesManager.get(key, defaultValue)
    }
}