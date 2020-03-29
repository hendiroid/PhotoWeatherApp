package com.app.photoweatherapp.base

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.photoweatherapp.app.UserApplication
import com.app.photoweatherapp.di.ApplicationComponent
import com.app.photoweatherapp.utils.LoadingDialog
import javax.inject.Inject

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {
    val component: ApplicationComponent
        get() = (activity?.application as UserApplication).component

    lateinit var dataBindingView: V

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val loadingDialog by lazy {
        val dialog = LoadingDialog(activity as Activity).build()
        dialog
    }
    abstract val getLayoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        init(inflater, container!!, getLayoutId)
        return dataBindingView.root
    }

    private fun init(inflater: LayoutInflater, container: ViewGroup, layoutId: Int) {
        dataBindingView = DataBindingUtil.inflate(inflater, layoutId, container, false)
    }

    abstract fun initializeComponents(view: View)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponents(view)
    }

}