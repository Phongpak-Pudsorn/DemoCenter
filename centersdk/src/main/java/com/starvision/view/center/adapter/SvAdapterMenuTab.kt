package com.starvision.view.center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemTabsBinding
import com.starvision.view.center.models.SvTabModels

class SvAdapterMenuTab(val context:Context, private val listTab:ArrayList<SvTabModels>, private val onClickListener:TabClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    class ViewHolder(val binding:ItemTabsBinding):RecyclerView.ViewHolder(binding.root)
    interface TabClickListener {
        fun onTabClick(position: Int)
    }
    var listener : TabClickListener? = null
    init {
        listener = onClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemTabsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        listTab[0].boo = true
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTab.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding.textType.text = listTab[position].name
            holder.binding.textType.setOnClickListener {
                clearColor()
                listTab[position].boo = true
                listener?.onTabClick(position)
            }
            holder.binding.textType.isChecked = listTab[position].boo
            if (listTab[position].boo) {
                holder.binding.textType.setTextColor(ContextCompat.getColor(context,R.color.bg_name_lucky))
                holder.binding.textType.setBackgroundResource(R.drawable.btn_underline_blue)
            } else {
                holder.binding.textType.setTextColor(ContextCompat.getColor(context,R.color.almost_white))
                holder.binding.textType.setBackgroundResource(R.color.white)
            }

        }
    }
    private fun clearColor() {
        for (i in listTab.indices){
            listTab[i].boo = false
            notifyDataSetChanged()
        }
    }
}