package com.app.photoweatherapp.utils.managers

import android.content.Context
import android.graphics.Bitmap
import com.app.photoweatherapp.db.PhotoEntity
import com.app.photoweatherapp.db.PhotosDataBase
import javax.inject.Inject

interface RoomDBManagerInterface {
    suspend fun insertPhoto(photo: String)
    suspend fun retrievePhotos(): ArrayList<String>
    suspend fun retrieveLastItem(): String
}

class RoomDBManager @Inject constructor(private val context: Context) : RoomDBManagerInterface {
    private val instance = PhotosDataBase.getInstance(context = context).photoItemDao()

    override suspend fun insertPhoto(photo: String) {
            instance.insertItemEntity(
                PhotoEntity(
                    photo = photo
                )
            )
    }

    override suspend fun retrievePhotos(): ArrayList<String> {
        val list = instance.getAllItemEntities()
        val data = ArrayList<String>()
        for (i in list) {
            data.add(i.photo)
        }
        return data
    }

    override suspend fun retrieveLastItem(): String {
        return instance.getLastItem().photo
    }
}