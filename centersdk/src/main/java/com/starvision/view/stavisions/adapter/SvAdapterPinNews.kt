package com.starvision.view.stavisions.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.databinding.ItemNewsPinBinding
import com.starvision.view.SvWebViewActivity
import com.starvision.view.center.models.SvCenterModels

class SvAdapterPinNews(val context:Context,val pinList:ArrayList<SvCenterModels.CenterData.PageData.NewsData.Pin>):RecyclerView.Adapter<SvAdapterPinNews.PinHolder>() {
    class PinHolder(val pinBinding: ItemNewsPinBinding):RecyclerView.ViewHolder(pinBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinHolder {
        val binding = ItemNewsPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PinHolder(binding)
    }

    override fun getItemCount(): Int = pinList.size

    override fun onBindViewHolder(holder: PinHolder, position: Int) {
        holder.pinBinding.tvTitle.text = pinList[position].newsappTitle
        Glide.with(holder.pinBinding.imgPin).load(pinList[position].newsappImgNews).into(holder.pinBinding.imgPin)
        holder.pinBinding.moreButton.setOnClickListener {
            if (SvConst.clickAble) {
                SvConst.clickAble = false
                val intent = Intent(context, SvWebViewActivity::class.java)
                intent.putExtra("link", pinList[position].newsappUrlNews)
                intent.putExtra("title", pinList[position].newsappTitle)
                context.startActivity(intent)
            }
        }
    }
}