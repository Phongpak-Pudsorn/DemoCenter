package com.starvision.view.center.sub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.data.SvConst
import com.starvision.centersdk.databinding.ItemMoreappBinding
import com.starvision.view.center.models.SvCenterModels

class SvAdapterMoreApp(
    private val context: FragmentActivity,
    private val appList: ArrayList<SvCenterModels.CenterData.PageData.IconData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMoreappBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            for (i in appList[position].iconappdatarow.indices) {
                Glide.with(context).load(appList[position].iconappdatarow[i].iconappImgicon).into(holder.binding.imgApp)
                holder.binding.tvDesApp.text = appList[position].iconappdatarow[i].iconappTitle
                holder.binding.tvNameApp.text = appList[position].iconappdatarow[i].iconappTitle
                holder.binding.root.setOnClickListener {
                    if (SvConst.clickAble) {
                        SvConst.clickAble = false
                        SvConst.openAnotherApp(
                            context,
                            appList[position].iconappdatarow[i].iconappLinkstoregoogle
                        )
                    }
                }
            }
        }
    }

    inner class ViewHolder(var binding: ItemMoreappBinding) : RecyclerView.ViewHolder(binding.root)
}