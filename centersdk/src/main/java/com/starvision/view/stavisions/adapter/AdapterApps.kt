package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.ItemAppsBinding
import com.starvision.view.stavisions.info.BannerInfo

class AdapterApps(val list:ArrayList<BannerInfo>): RecyclerView.Adapter<AdapterApps.AppsHolder>() {

    class AppsHolder(val appsBinding: ItemAppsBinding):RecyclerView.ViewHolder(appsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsHolder {
        val binding= ItemAppsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppsHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AppsHolder, position: Int) {
        holder.appsBinding.tvName.text = "Apps $position"
    }


}