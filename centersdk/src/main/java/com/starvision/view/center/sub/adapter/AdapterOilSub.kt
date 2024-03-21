package com.starvision.view.center.sub.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.luckygamesdk.databinding.LayoutExchangeItemBinding
import com.starvision.luckygamesdk.databinding.LayoutOilItemBinding
import com.starvision.view.center.sub.models.SubOilTodayModel

class AdapterOilSub(val context:Context,val list:ArrayList<SubOilTodayModel>):RecyclerView.Adapter<AdapterOilSub.OilHolder>() {
    class OilHolder(val oilBinding:LayoutOilItemBinding):RecyclerView.ViewHolder(oilBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OilHolder {
        val binding = LayoutOilItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OilHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OilHolder, position: Int) {
        holder.oilBinding.mTvToDayOil.text = list[position].priceToday
        holder.oilBinding.mTvNameOil.text = list[position].priceName
        if (list[position].priceImg1!=""){
            Glide.with(context).load(list[position].priceImg1).into(holder.oilBinding.imgOil1)
        }
        if (list[position].priceImg2!=""){
            Glide.with(context).load(list[position].priceImg2).into(holder.oilBinding.imgOil2)
        }
        if (list[position].priceImg3!=""){
            Glide.with(context).load(list[position].priceImg3).into(holder.oilBinding.imgOil3)
        }
        if (list[position].priceImg4!=""){
            Glide.with(context).load(list[position].priceImg4).into(holder.oilBinding.imgOil4)
        }
        try {
            val inputs = context.assets.open("oil/"+list[position].imgOil+".png")
            val img = Drawable.createFromStream(inputs,null)
            holder.oilBinding.imgIcOil.setImageDrawable(img)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}