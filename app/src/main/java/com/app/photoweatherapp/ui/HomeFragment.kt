package com.app.photoweatherapp.ui

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.photoweatherapp.R
import com.app.photoweatherapp.base.BaseFragment
import com.app.photoweatherapp.databinding.FragmentHomeBinding
import com.app.photoweatherapp.models.CityModel
import com.app.photoweatherapp.models.DataModel
import com.app.photoweatherapp.utils.SnackBarUtils
import com.app.photoweatherapp.utils.ViewState
import com.app.photoweatherapp.utils.getRealPathFromURI
import com.app.photoweatherapp.utils.imageview.load
import com.app.photoweatherapp.utils.loadJSONFromAsset
import com.app.photoweatherapp.utils.managers.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.File


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val getLayoutId: Int
        get() = R.layout.fragment_home

    private lateinit var viewModel: MainViewModel
    private lateinit var screenShotManager: ScreenShotManagerInterface
    private lateinit var roomDBManager: RoomDBManagerInterface
    private lateinit var runtimePermissionsManager: RuntimePermissionsManagerInterface
    private var imageBitmap: Bitmap? = null
    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var fabClock: Animation? = null
    private var fabAntiClock: Animation? = null

    private var isOpen = false
    private var isCityAvailable = false
    private var isSaved = false

    private val PHOTO_REQUEST_CODE = 100

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun initializeComponents(view: View) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        dataBindingView.viewModel = viewModel
        dataBindingView.model = DataModel()
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fabClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock);
        fabAntiClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock);

        screenShotManager = ScreenShotManager(dataBindingView.view)
        runtimePermissionsManager = RuntimePermissionsManager(context!!, 1)
        roomDBManager = RoomDBManager(context!!)

        CoroutineScope(Dispatchers.IO).launch {
            if (roomDBManager.retrievePhotos().isNotEmpty()) {
                val lastPhoto = roomDBManager.retrieveLastItem()
                withContext(Dispatchers.Main) {
                    dataBindingView.lastWeatherPhoto.visibility = View.VISIBLE
                    dataBindingView.weatherPhoto.visibility = View.GONE
                    dataBindingView.lastWeatherPhoto.load(Uri.fromFile(File(lastPhoto)))
                }
            }
        }

        dataBindingView.fabMain.setOnClickListener {
            if (imageBitmap == null) {
                dispatchTakePictureIntent()
            } else {
                fabClickListener()
            }
        }
        dataBindingView.fabShare.setOnClickListener {
            if (isCityAvailable) {
                if (runtimePermissionsManager.isPermissionGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    sharePage()
                } else {
                    runtimePermissionsManager.showRequestPermissionsDialog(Array(1) { android.Manifest.permission.WRITE_EXTERNAL_STORAGE })
                }
            } else {
                SnackBarUtils.showErrorSnackBar(
                    context!!,
                    dataBindingView.view,
                    "Select city first to share photo"
                )
            }
        }

        dataBindingView.selectCityTV.setOnClickListener {
            getDataFromJsonFile()
        }
        setUpObservers()
    }

    private fun getDataFromJsonFile() {
        loadingDialog.show()
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val jsonArray = JSONArray(loadJSONFromAsset(context!!)!!)
                withContext(Dispatchers.Main) {
                    val list = ArrayList<CityModel>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("name")
                        val country = jsonObject.getString("country")
                        val model = CityModel(name, country)
                        //Add your values in your `ArrayList` as below:
                        list.add(model)
                    }
                    displayCitiesDialog(list)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            imageBitmap = data!!.extras!!.get("data") as Bitmap
            dataBindingView.weatherPhoto.visibility = View.VISIBLE
            dataBindingView.weatherPhoto.setImageBitmap(imageBitmap)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayCitiesDialog(cities: ArrayList<CityModel>) {
        loadingDialog.dismiss()
        AlertDialog.Builder(context!!)
            .setItems(cities.map { "${it.city} - ${it.country}" }
                .toTypedArray()) { dialog, which ->
                dataBindingView.selectCityTV.text =
                    "${cities[which].city} - ${cities[which].country}"
                viewModel.getWeatherData(cities[which].city)
                dialog.dismiss()
            }
            .create().show()
    }

    private fun setUpObservers() {
        viewModel.weatherDataViewState.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    dataBindingView.bannerCL.visibility = View.VISIBLE
                    dataBindingView.selectCityTV.visibility = View.GONE
                    loadingDialog.dismiss()
                    isCityAvailable = true
                    dataBindingView.model = it.data
                    dataBindingView.lastWeatherPhoto.visibility = View.GONE
                    dataBindingView.descTV.text = it.data.weather[0].description
                }

                is ViewState.Error -> {
                    dataBindingView.bannerCL.visibility = View.GONE
                    loadingDialog.dismiss()
                    SnackBarUtils.showErrorSnackBar(
                        context!!,
                        dataBindingView.root,
                        it.message.errorMessage
                    )
                }
            }
        })
    }

    private fun sharePage() {
        SnackBarUtils.showSuccessSnackBar(context!!, dataBindingView.root, "Loading...")
        CoroutineScope(Dispatchers.IO).launch {
            val path =
                screenShotManager.saveScreenShotToGeneralPicturesPath(activity as Context)
            roomDBManager.insertPhoto(getRealPathFromURI(context!!, Uri.parse(path)))

            withContext(Dispatchers.Main) {
                val uri = Uri.parse(path)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/png"
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(intent, getString(R.string.share_image)))
            }
        }
    }

    private fun fabClickListener() {
        isOpen = if (isOpen) {
            fabShare.startAnimation(fabClose)
            fabMain.startAnimation(fabAntiClock)
            fabShare.isClickable = false
            false
        } else {
            fabShare.startAnimation(fabOpen)
            fabMain.startAnimation(fabClock)
            fabShare.isClickable = true
            true
        }
    }

}