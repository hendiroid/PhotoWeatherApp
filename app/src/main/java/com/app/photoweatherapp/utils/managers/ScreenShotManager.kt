package com.app.photoweatherapp.utils.managers

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

interface ScreenShotManagerInterface {
    fun getScreenShotBitmap(): Bitmap
    fun saveScreenShotToGeneralPicturesPath(context: Context): String
    fun saveScreenShot(): Boolean
}

class ScreenShotManager(private val view: View) : ScreenShotManagerInterface {

    private val internalStoragePicturesPath = "storage/emulated/0/Pictures/"

    override fun getScreenShotBitmap(): Bitmap {
        view.isDrawingCacheEnabled = true

        return view.drawingCache
    }

    override fun saveScreenShotToGeneralPicturesPath(context: Context): String {
        //to store in pictures folder directly
        return MediaStore.Images.Media.insertImage(context.contentResolver, getScreenShotBitmap(), "Screen", "screen")
    }

    override fun saveScreenShot(): Boolean {

        val directory = File(internalStoragePicturesPath)
        if (!directory.exists()) {
            directory.mkdir()
        }

        val fullImagePath = File(directory, System.currentTimeMillis().toString() + ".png")

        val fos: FileOutputStream?

        return try {
            fos = FileOutputStream(fullImagePath)
            getScreenShotBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            true
        } catch (e: FileNotFoundException) {
            false
        } catch (e: Exception) {
            false
        }
    }

}