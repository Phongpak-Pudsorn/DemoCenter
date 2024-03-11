package com.starvision.view.luckygamesdk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.ItemTabsBinding

class AdapterMenuTab(val context:Context,val listTab:Array<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    class ViewHolder(val binding:ItemTabsBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemTabsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTab.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    fun clearColor(){
    }
}