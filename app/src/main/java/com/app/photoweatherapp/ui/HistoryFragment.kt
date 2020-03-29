package com.app.photoweatherapp.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.photoweatherapp.R
import com.app.photoweatherapp.base.BaseFragment
import com.app.photoweatherapp.databinding.FragmentHistoryBinding
import com.app.photoweatherapp.utils.managers.RoomDBManager
import com.app.photoweatherapp.utils.managers.RoomDBManagerInterface
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.ArrayList

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    private lateinit var roomDBManager: RoomDBManagerInterface

    override val getLayoutId: Int
        get() = R.layout.fragment_history

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun initializeComponents(view: View) {
        roomDBManager = RoomDBManager(context!!)
        dataBindingView.photosRV.layoutManager =
            GridLayoutManager(context!!, 2)
        var list: ArrayList<String>
        CoroutineScope(Dispatchers.IO).launch {
            list = roomDBManager.retrievePhotos()
            withContext(Dispatchers.Main) {
                dataBindingView.photosRV.adapter = PhotosAdapter(context!!, list)
            }
        }
    }
}