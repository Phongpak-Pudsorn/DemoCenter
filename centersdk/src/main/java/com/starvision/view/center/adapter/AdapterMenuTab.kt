package com.starvision.view.center.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemTabsBinding

class AdapterMenuTab(val context:Context,val listTab:MutableList<Pair<String,Boolean>>,val onClickListener:tabClickLiatener): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    class ViewHolder(val binding:ItemTabsBinding):RecyclerView.ViewHolder(binding.root)
    interface tabClickLiatener {
        fun onTabClick(position: Int)
    }
    var listener : tabClickLiatener? = null
    init {
        listener = onClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemTabsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTab.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding.textType.text = listTab[position].first
            holder.binding.tabMenu.setOnClickListener {
                clearColor(position)
                listener?.onTabClick(position)
//                notifyDataSetChanged()
            }
            holder.binding.textType.isChecked = listTab[position].second
            if (listTab[position].second) {
                holder.binding.textType.setBackgroundResource(R.drawable.btn_underline_black)
            } else {
                holder.binding.textType.setBackgroundResource(R.color.white)
            }

        }
    }
    private fun clearColor(position: Int) {
        listTab.clear()
        when (position) {
            0 -> {
                listTab.add(Pair("Stavision",true))
                listTab.add(Pair("Lucky Game",false))
                listTab.add(Pair("Playplay+",false))
            }
            1 -> {
                listTab.add(Pair("Stavision",false))
                listTab.add(Pair("Lucky Game",true))
                listTab.add(Pair("Playplay+",false))
            }
            2 -> {
                listTab.add(Pair("Stavision",false))
                listTab.add(Pair("Lucky Game",false))
                listTab.add(Pair("Playplay+",true))
            }
        }
        notifyDataSetChanged()
    }
}