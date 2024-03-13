package com.starvision.view.center.sub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.view.center.sub.models.SubLottothaiModels
import java.util.ArrayList

class AdapterLottothaiSub(private val listData: ArrayList<SubLottothaiModels>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var string = "  "
    private val TAG = javaClass.simpleName


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_office, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.item_off.text = listData[position].Datarow[position].suggest_name.replace(" ", string)
            holder.item_three.text = listData[position].Datarow[position].top_third.replace(" ", string)
            holder.item_two_up.text = listData[position].Datarow[position].top_second.replace(" ", string)
            holder.item_two_down.text = listData[position].Datarow[position].bottom_second.replace(" ", string)
            if (listData[position].Datarow[position].top_third == "") holder.item_three.text = "-"
            if (listData[position].Datarow[position].top_second == "") holder.item_two_up.text = "-"
            if (listData[position].Datarow[position].bottom_second == "") holder.item_two_down.text = "-"
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var item_off = view.findViewById<View>(R.id.item_off) as TextView
        var item_three = view.findViewById<View>(R.id.item_three) as TextView
        var item_two_up = view.findViewById<View>(R.id.item_two_up) as TextView
        var item_two_down = view.findViewById<View>(R.id.item_two_down) as TextView

    }
}