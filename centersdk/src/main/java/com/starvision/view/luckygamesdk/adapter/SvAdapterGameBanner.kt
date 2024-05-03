package com.starvision.view.luckygamesdk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.databinding.ItemLuckyBannerBinding

class SvAdapterGameBanner(context: Context): RecyclerView.Adapter<SvAdapterGameBanner.BannerHolder>() {
    class BannerHolder(val bannerBinding: ItemLuckyBannerBinding):RecyclerView.ViewHolder(bannerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val binding = ItemLuckyBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BannerHolder(binding)
    }

    override fun getItemCount(): Int = 6

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
    }
}