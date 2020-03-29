package com.app.photoweatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemEntity(ad: PhotoEntity): Long

    @Query("select * from photosItems ORDER BY generatedId DESC")
    fun getAllItemEntities(): List<PhotoEntity>

    @Query("select * from photosItems ORDER BY generatedId DESC LIMIT 1")
    fun getLastItem(): PhotoEntity
}