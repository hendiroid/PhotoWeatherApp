package com.app.photoweatherapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D : ViewDataBinding, M : Any>(
    open var context: Context,
    open var listData: List<M>
) : RecyclerView.Adapter<BaseAdapter<D, M>.MyViewHolder>() {

    lateinit var binding: D

    inner class MyViewHolder(val binding: D) : RecyclerView.ViewHolder(binding.root)

    abstract fun layoutID(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutID(), parent, false)
        return MyViewHolder(binding)
    }

    fun updateAll(tasks: List<M>) {
        listData = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}
