package com.app.photoweatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class PhotosDataBase : RoomDatabase() {

    abstract fun photoItemDao(): PhotosDao

    companion object {

        @Volatile
        private var INSTANCE: PhotosDataBase? = null

        fun getInstance(context: Context): PhotosDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PhotosDataBase::class.java, "photoWeather.db"
            ).allowMainThreadQueries()
                .build()
    }
}