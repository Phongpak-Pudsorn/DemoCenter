package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemAppsBannerBinding
import com.starvision.luckygamesdk.databinding.ItemDotsBinding
import com.starvision.view.stavisions.info.BannerInfo

class AdapterDots(context: Context,val list:ArrayList<BannerInfo>):RecyclerView.Adapter<AdapterDots.DotsHolder>() {
    class DotsHolder(val dotsBinding: ItemDotsBinding):RecyclerView.ViewHolder(dotsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotsHolder {
        val binding= ItemDotsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DotsHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DotsHolder, position: Int) {
        if (list[position].boo) {
            holder.dotsBinding.imgDot.setImageResource(R.drawable.tab_indicator_selected)
        }else{
            holder.dotsBinding.imgDot.setImageResource(R.drawable.tab_indicator_default)
        }
    }
}