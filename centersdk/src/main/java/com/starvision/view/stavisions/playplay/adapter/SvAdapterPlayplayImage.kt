package com.starvision.view.stavisions.playplay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.databinding.ItemPppBannerBinding

class SvAdapterPlayplayImage(context: Context):
    RecyclerView.Adapter<SvAdapterPlayplayImage.ImageHolder>(){

    class ImageHolder(val imgBinding:ItemPppBannerBinding):RecyclerView.ViewHolder(imgBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ImageHolder {
        val binding= ItemPppBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageHolder(binding)
    }
    override fun getItemCount(): Int = 6

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
    }


}