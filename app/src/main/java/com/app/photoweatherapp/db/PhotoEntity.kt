package com.app.photoweatherapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photosItems")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "generatedId")
    val generatedId: Int = 0,

    @ColumnInfo(name = "photo")
    val photo: String = ""
)