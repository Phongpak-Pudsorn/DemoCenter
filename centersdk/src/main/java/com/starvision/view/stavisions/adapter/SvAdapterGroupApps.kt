package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemAppsInnerBinding
import com.starvision.view.center.models.SvCenterModels

class SvAdapterGroupApps(val context: Context, val list:ArrayList<SvCenterModels.CenterData.PageData.IconData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_HEADER = 1
    val VIEW_TYPE_APP = 2
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
//            holder.groupBinding.tvAppMore.text = context.getString(R.string.text_moreapp)
            holder.groupBinding.rvApp.apply {
                adapter = SvAdapterApps(context,list[position].iconappdatarow)
                layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            }
            if (position!=0){
                holder.groupBinding.tvAppMore.visibility = View.GONE
            }
        }

    }


}