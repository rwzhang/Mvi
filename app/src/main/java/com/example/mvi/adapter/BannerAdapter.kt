package com.example.mvi.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mvi.R
import com.example.mvi.base.BaseRecyclerAdapter
import com.example.mvi.data.bean.BannerBean
import com.example.mvi.databinding.ItemBannerBinding

class BannerAdapter : BaseRecyclerAdapter<BannerAdapter.ViewHolder, BannerBean>() {
    private lateinit var binding: ItemBannerBinding;

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun bindView(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        var data = getItemData(position)
        binding.imageViewBanner.load(data?.imagePath)
    }

    override fun getResourcesId(viewType: Int): Int {
        return R.layout.item_banner
    }

    override fun getViewHolder(view: View, parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.bind(view)!!
        return ViewHolder(binding.root)
    }

    override fun getReItemViewType(position: Int): Int {
        return position
    }

}