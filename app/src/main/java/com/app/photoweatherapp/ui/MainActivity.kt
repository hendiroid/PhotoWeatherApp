package com.app.photoweatherapp.ui

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.app.photoweatherapp.R
import com.app.photoweatherapp.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_app_bar.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView.setNavigationItemSelectedListener(this)
        displayFragment(HomeFragment())

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toggle.isDrawerIndicatorEnabled = true
        toggle.toolbarNavigationClickListener = View.OnClickListener {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        closeDrawer()
        handleDrawerListener(menuItem)

        return true
    }

    private fun handleDrawerListener(it: MenuItem) {
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerClosed(drawerView: View) {
                displayFragmentFromNavigationView(it.itemId)
                drawerLayout.removeDrawerListener(this)
            }

            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

        })
    }

    private fun displayFragmentFromNavigationView(itemId: Int) {
        toolBar.menu.clear()

        when (itemId) {
            R.id.home -> {
                titleTextView.text = ""
                displayFragment(HomeFragment())
            }

            R.id.history -> {
                titleTextView.text = "History"
                displayFragment(HistoryFragment())
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    private fun closeDrawer() {
        Handler().post {
            drawerLayout.closeDrawer(Gravity.LEFT)
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.homeFragment, fragment).commit()
    }
}
