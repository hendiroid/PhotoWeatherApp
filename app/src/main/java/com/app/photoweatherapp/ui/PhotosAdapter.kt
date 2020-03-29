package com.app.photoweatherapp.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import com.app.photoweatherapp.R
import com.app.photoweatherapp.base.BaseAdapter
import com.app.photoweatherapp.databinding.ItemPhotoBinding
import com.app.photoweatherapp.utils.imageview.load
import com.bumptech.glide.Glide
import java.io.File


class PhotosAdapter(
    override var context: Context,
    override var listData: List<String>
) : BaseAdapter<ItemPhotoBinding, String>(context, listData) {
    override fun layoutID(): Int =
        R.layout.item_photo

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val file = File(listData[position])
        val imageUri = Uri.fromFile(file)
        binding.weatherPhoto.load(imageUri)
    }
}