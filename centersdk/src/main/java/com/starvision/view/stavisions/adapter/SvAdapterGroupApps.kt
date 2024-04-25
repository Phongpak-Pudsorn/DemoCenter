package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.databinding.ItemAppsInnerBinding
import com.starvision.view.center.models.SvCenterModels

class SvAdapterGroupApps(val context: Context, val list:ArrayList<SvCenterModels.CenterData.PageData.IconData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_HEADER = 1
    val VIEW_TYPE_APP = 2
    var appList = ArrayList<SvCenterModels.CenterData.PageData.IconData.IconDatarow>()
    class GroupHolder(val groupBinding: ItemAppsInnerBinding):RecyclerView.ViewHolder(groupBinding.root)
//    class HeaderHolder(val headerBinding: ItemNewsHeaderBinding):RecyclerView.ViewHolder(headerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = ItemAppsInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GroupHolder(binding)
    }

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GroupHolder){
            holder.groupBinding.tvAppType.text = list[position].iconappgroupname
            holder.groupBinding.tvAppMore.visibility = View.GONE
            holder.groupBinding.rvApp.apply {
                adapter = SvAdapterApps(context,list[position].iconappdatarow)
                layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            }
        }

    }



}