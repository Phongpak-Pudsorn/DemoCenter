package com.starvision.view.center.sub.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.centersdk.databinding.LayoutOilFooterBinding
import com.starvision.centersdk.databinding.LayoutOilItemBinding
import com.starvision.view.center.sub.models.SvSubOilTodayModels

class SvAdapterOilSub(val context:Context, val list:ArrayList<SvSubOilTodayModels>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_ITEM = 1
    val VIEW_TYPE_FOOTER = 2
    class OilHolder(val oilBinding:LayoutOilItemBinding):RecyclerView.ViewHolder(oilBinding.root)
    class FootHolder(val footBinding:LayoutOilFooterBinding):RecyclerView.ViewHolder(footBinding.root)

    override fun getItemViewType(position: Int): Int {
        return if (list[position].priceType=="footer"){
            VIEW_TYPE_FOOTER
        }else{
            VIEW_TYPE_ITEM
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==VIEW_TYPE_ITEM) {
            val binding = LayoutOilItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OilHolder(binding)
        }else{
            val binding = LayoutOilFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FootHolder(binding)
        }
    }

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OilHolder){
            holder.oilBinding.mTvToDayOil.text = list[position].priceToday
            holder.oilBinding.mTvNameOil.text = list[position].priceName
            val imgArr = list[position].priceImg1.split(",")
            Glide.with(context).load(imgArr[1]).into(holder.oilBinding.imgOil1)
            try {
                Glide.with(context).load(imgArr[2]).into(holder.oilBinding.imgOil2)
            }catch (e:Exception){
                Glide.with(context).load("").into(holder.oilBinding.imgOil2)
            }
//        try {
//            Glide.with(context).load(imgArr[3]).into(holder.oilBinding.imgOil3)
//        }catch (e:Exception){
//            Glide.with(context).load("").into(holder.oilBinding.imgOil3)
//        }
//        try {
//            Glide.with(context).load(imgArr[4]).into(holder.oilBinding.imgOil4)
//        }catch (e:Exception){
//            Glide.with(context).load("").into(holder.oilBinding.imgOil4)
//        }
            try {
                val inputs = context.assets.open("oil/"+list[position].imgOil+".png")
                val img = Drawable.createFromStream(inputs,null)
                holder.oilBinding.imgIcOil.setImageDrawable(img)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }else if (holder is FootHolder){

        }
    }


}