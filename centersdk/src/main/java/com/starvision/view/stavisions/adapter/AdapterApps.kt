package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemAppsBinding
import com.starvision.view.stavisions.info.BannerInfo
import com.starvision.view.stavisions.info.IconInfo

class AdapterApps(val context: Context,val list:ArrayList<IconInfo>): RecyclerView.Adapter<AdapterApps.AppsHolder>() {

    class AppsHolder(val appsBinding: ItemAppsBinding):RecyclerView.ViewHolder(appsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsHolder {
        val binding= ItemAppsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppsHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AppsHolder, position: Int) {
        holder.appsBinding.tvName.text = list[position].iconTitle
        holder.appsBinding.imgApp.setImageResource(setImage(position))
        holder.appsBinding.root.setOnClickListener {
            Const.openAnotherApp(context,list[position].iconLinkkeyopenapp)
        }
    }
    private fun setImage(position: Int): Int {
        var img = 0
        when(position){
            0-> img = R.mipmap.ic_exchange
            1-> img = R.mipmap.ic_zodiac
            2-> img = R.mipmap.ic_gold
            3-> img = R.mipmap.ic_oil
            4-> img = R.mipmap.ic_lucky
            5-> img = R.mipmap.ic_lottery
        }
        return img
    }


}