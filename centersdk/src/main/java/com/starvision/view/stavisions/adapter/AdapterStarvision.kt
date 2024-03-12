package com.starvision.view.stavisions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.ItemNewsBinding
import com.starvision.luckygamesdk.databinding.ItemTabsBinding

class AdapterStarvision: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_NEWS = 1
    val VIEW_TYPE_BANNER = 2
    class ViewHolder(val newsBinding: ItemNewsBinding):RecyclerView.ViewHolder(newsBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding=ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            holder.newsBinding.tvTitle.text = "News$position"
        }

    }
}