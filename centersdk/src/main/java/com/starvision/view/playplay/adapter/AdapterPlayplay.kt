package com.starvision.view.playplay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.ItemNewsBinding
import com.starvision.luckygamesdk.databinding.ItemPlayplayBinding
import com.starvision.luckygamesdk.databinding.ItemPlayplayHeaderBinding

class AdapterPlayplay:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_HEADER = 1
    val VIEW_TYPE_EPISODE = 2
    override fun getItemViewType(position: Int): Int {
        if (position==0){
            return VIEW_TYPE_HEADER
        }else{
            return VIEW_TYPE_EPISODE
        }
    }
    class ViewHolder(val epBinding: ItemPlayplayBinding):RecyclerView.ViewHolder(epBinding.root)
    class headerHolder(val headBinding: ItemPlayplayHeaderBinding):RecyclerView.ViewHolder(headBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==VIEW_TYPE_HEADER){
            val binding = ItemPlayplayHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return headerHolder(binding)
        }else {
            val binding = ItemPlayplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            holder.epBinding.tvEpisode.text = "$position"
            holder.epBinding.tvDate.text = "3/13/2024"
        }else if (holder is headerHolder){
            holder.headBinding.tvTitle.text = "Dragonball DAIMA"
            holder.headBinding.tvSummary.text = "the last story of Dragon ball that Akira Toriyama left in this world will on air in 2024"
        }
    }
}