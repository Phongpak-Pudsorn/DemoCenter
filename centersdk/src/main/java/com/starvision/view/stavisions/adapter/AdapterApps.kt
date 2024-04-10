package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemAppsBinding
import com.starvision.view.center.models.CenterModels

class AdapterApps(val context: Context,val list:ArrayList<CenterModels.CenterData.PageData.IconData>): RecyclerView.Adapter<AdapterApps.AppsHolder>() {

    class AppsHolder(val appsBinding: ItemAppsBinding):RecyclerView.ViewHolder(appsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsHolder {
        val binding= ItemAppsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppsHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AppsHolder, position: Int) {
        holder.appsBinding.tvName.text = list[position].iconappTitle
        Glide.with(holder.appsBinding.imgApp).load(list[position].iconappImgicon).into(holder.appsBinding.imgApp)
        holder.appsBinding.root.setOnClickListener {
            if (Const.clickAble) {
                Const.clickAble = false
                Const.openAnotherApp(context, list[position].iconappLinkstoregoogle)
            }
        }
    }


}