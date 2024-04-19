package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.databinding.ItemAppsBinding
import com.starvision.luckygamesdk.databinding.ItemNewsHeaderBinding
import com.starvision.view.center.models.SvCenterModels

class SvAdapterApps(val context: Context, val list:ArrayList<SvCenterModels.CenterData.PageData.IconData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_HEADER = 1
    val VIEW_TYPE_APP = 2
    class AppsHolder(val appsBinding: ItemAppsBinding):RecyclerView.ViewHolder(appsBinding.root)
    class HeaderHolder(val headerBinding: ItemNewsHeaderBinding):RecyclerView.ViewHolder(headerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_HEADER) {
            val binding = ItemNewsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderHolder(binding)
        } else {
            val binding = ItemAppsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AppsHolder(binding)
        }
    }

    override fun getItemCount(): Int = list.size
    override fun getItemViewType(position: Int): Int {
        if (list[position].iconappgroupId == "1"){
            return VIEW_TYPE_HEADER
        }else{
            return VIEW_TYPE_APP
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppsHolder) {
//            for (i in list[position].iconappdatarow.indices) {
//                holder.appsBinding.tvName.text = list[position].iconappdatarow[i].iconappTitle
//                Glide.with(holder.appsBinding.imgApp).load(list[position].iconappdatarow[i].iconappImgicon).into(holder.appsBinding.imgApp)
//                holder.appsBinding.root.setOnClickListener {
//                    if (SvConst.clickAble) {
//                        SvConst.clickAble = false
//                        SvConst.openAnotherApp(context, list[position].iconappdatarow[i].iconappLinkstoregoogle)
//                    }
//                }
//            }
        }else if (holder is HeaderHolder){
            holder.headerBinding.tvHeader.text = list[position].iconappgroupId
        }
    }


}