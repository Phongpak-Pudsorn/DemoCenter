package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.ItemAppsBannerBinding
import com.starvision.luckygamesdk.databinding.ItemAppsBinding
import com.starvision.luckygamesdk.databinding.ItemNewsBinding
import com.starvision.view.stavisions.info.BannerInfo

class AdapterImageSlide(context: Context,val bannerList:ArrayList<BannerInfo>):RecyclerView.Adapter<AdapterImageSlide.ImageHolder>(){
    interface OnDataPass{
        fun passData(packName:String)
    }
    private var dataPasser: OnDataPass
    init {
        dataPasser = context as OnDataPass
    }
    class ImageHolder(val imgBinding:ItemAppsBannerBinding):RecyclerView.ViewHolder(imgBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding= ItemAppsBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int = bannerList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.imgBinding.tvContent.text = bannerList[position].bannerTitle
        holder.imgBinding.root.setOnClickListener {
            dataPasser.passData(bannerList[position].bannerLinkstoreapp)
        }
    }
}