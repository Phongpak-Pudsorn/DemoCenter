package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.data.SvConst
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.ItemAppsBinding
import com.starvision.centersdk.databinding.ItemAppsInnerBinding
import com.starvision.view.center.models.SvCenterModels

class SvAdapterApps(val context:Context,val list: ArrayList<SvCenterModels.CenterData.PageData.IconData.IconDatarow>): RecyclerView.Adapter<SvAdapterApps.AppsHolder>() {
    class AppsHolder(val appsBinding: ItemAppsBinding):RecyclerView.ViewHolder(appsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsHolder {
        val binding = ItemAppsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppsHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AppsHolder, position: Int) {
        holder.appsBinding.tvName.text = list[position].iconappTitle
        Glide.with(holder.appsBinding.imgApp).load(list[position].iconappImgicon).placeholder(R.mipmap.sv_ic_luancher_round).into(holder.appsBinding.imgApp)
        holder.appsBinding.root.setOnClickListener {
            if (SvConst.clickAble) {
                SvConst.clickAble = false
                SvConst.openAnotherApp(context, list[position].iconappLinkstoregoogle)
            }
        }
    }
}