package com.app.photoweatherapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.photoweatherapp.R
import com.google.android.material.snackbar.Snackbar


object SnackBarUtils {

    fun showErrorSnackBar(context: Context, coordinatorLayout: View, message: String): Snackbar {
        val snackBar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
        val textView: TextView = sbView.findViewById(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        textView.gravity = Gravity.CENTER
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        snackBar.show()
        return snackBar
    }

    fun showSuccessSnackBar(context: Context, coordinatorLayout: View, message: String): Snackbar {
        val snackBar = Snackbar
            .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        val textView: TextView = sbView.findViewById(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        textView.gravity = Gravity.CENTER
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
       snackBar.show()
        return snackBar
    }
}