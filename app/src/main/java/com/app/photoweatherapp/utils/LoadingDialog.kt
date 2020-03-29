package com.app.photoweatherapp.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.app.photoweatherapp.R

class LoadingDialog (private val context: Context) {

    fun build(): AlertDialog {

        val view = (context as Activity).layoutInflater.inflate(R.layout.layout_loading_dialog, null)

        val dialog = AlertDialog.Builder(context)
                .create()

        dialog.setCanceledOnTouchOutside(false)
        dialog.setView(view)

        return dialog
    }
}