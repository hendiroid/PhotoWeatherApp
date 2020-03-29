package com.app.photoweatherapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun loadJSONFromAsset(context: Context): String? {
    val json: String?
    json = try {
        val `is`: InputStream = context.assets.open("city.list.json")
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        val charset: Charset = Charsets.UTF_8
        String(buffer, charset)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun convertFromKelvinToCelsius(degree: Double): Int {
    return (degree - 273).toInt()
}

fun getRealPathFromURI(context: Context, uri: Uri): String {
    val cursor = context.contentResolver.query(uri, null, null, null, null)!!
    cursor.moveToFirst()
    val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
    val str = cursor.getString(idx)
    cursor.close()
    return str
}
