package com.app.photoweatherapp.base

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.photoweatherapp.app.UserApplication
import com.app.photoweatherapp.di.ApplicationComponent
import com.app.photoweatherapp.utils.LoadingDialog
import kotlinx.android.synthetic.main.layout_app_bar.*
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val component: ApplicationComponent
        get() = (application as UserApplication).component

    val loadingDialog by lazy {
        val dialog = LoadingDialog(this).build()
        dialog
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appResources: Resources

    fun setupToolBar(activityTitle: String) {
        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        titleTextView.text = activityTitle
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item!!)
    }
}