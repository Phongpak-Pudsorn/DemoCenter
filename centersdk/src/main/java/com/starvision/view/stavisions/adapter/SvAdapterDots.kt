package com.starvision.view.stavisions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.ItemDotsBinding
import com.starvision.view.center.models.SvCenterModels

class SvAdapterDots(position: Int, var list:ArrayList<SvCenterModels.CenterData.PageData.BannerData>):RecyclerView.Adapter<SvAdapterDots.DotHolder>() {
    private val pos = position
    class DotHolder(val dotsBinding: ItemDotsBinding):RecyclerView.ViewHolder(dotsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotHolder {
        val binding= ItemDotsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DotHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size>=2){
            list.size-2
        }else{
            list.size
        }
    }

    override fun onBindViewHolder(holder: DotHolder, position: Int) {
        clearColor(pos)
        if (list.size==1){
            list[0].boo = true
        }
        if (list[position].boo){
            holder.dotsBinding.imgDot.setImageResource(R.drawable.tab_indicator_selected)
        }else{
            holder.dotsBinding.imgDot.setImageResource(R.drawable.tab_indicator_default)
        }
    }
    fun clearColor(position: Int){
        for (i in list.indices){
            list[i].boo = false
        }
        if (1<=position && position<=list.size-1) {
            list[position - 1].boo = true
        }
    }
}