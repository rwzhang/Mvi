package com.example.mvi.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by zhangrenwei on 2020/5/19 5:42 PM.
 */
abstract class BaseRecyclerAdapter<VH : RecyclerView.ViewHolder, T> :
    RecyclerView.Adapter<VH>() {
    private var mData: MutableList<T>? = null
    private val resourceId: Int = 0
    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        mContext = parent.context
        val resourceId: Int = getResourcesId(viewType)
        val view = getView(resourceId, parent, false)
        return getViewHolder(view, parent,viewType)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        bindView(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return getReItemViewType(position)
    }

    fun setData(mData: MutableList<T>?) {
        this.mData = mData
    }

    fun getData(): MutableList<T>? {
        return mData
    }

    fun getItemData(position: Int): T? {
        return mData?.get(position)
    }

    fun refreshItem(position: Int) {
        mData?.get(position)
        notifyItemChanged(position)
    }


    abstract fun getResourcesId(viewType: Int): Int
    fun getView(resourceId: Int): View {
        return getView(resourceId, null)
    }

    fun getView(resourceId: Int, viewgroup: ViewGroup?): View {
        return getView(resourceId, viewgroup)
    }

    fun getView(resourcesId: Int, viewgroup: ViewGroup, attachToRoot: Boolean): View {
        return LayoutInflater.from(viewgroup.context).inflate(resourcesId, viewgroup, attachToRoot)
    }

    abstract fun getViewHolder(view: View,parent: ViewGroup, viewType: Int): VH
    abstract fun getReItemViewType(position: Int): Int
    abstract fun bindView(holder: VH, position: Int, payloads: MutableList<Any>)


}