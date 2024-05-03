package com.starvision.view.stavisions.playplay.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.databinding.ItemPppBinding

class SvAdapterPlayplayList: RecyclerView.Adapter<SvAdapterPlayplayList.ListHolder>() {
    class ListHolder(val seriesBinding: ItemPppBinding):RecyclerView.ViewHolder(seriesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = ItemPppBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListHolder(binding)
    }

    override fun getItemCount(): Int = 30

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
    }
}