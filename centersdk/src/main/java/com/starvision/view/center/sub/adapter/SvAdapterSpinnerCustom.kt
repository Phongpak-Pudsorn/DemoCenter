package com.starvision.view.center.sub.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.starvision.centersdk.R

class SvAdapterSpinnerCustom(context: Context, private val listData : ArrayList<String>) : BaseAdapter() {
    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var viewHolder: ViewHolder
    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
        var convertView = convertView
            viewHolder = ViewHolder()
            convertView = mInflater.inflate(R.layout.item_spinner, null)
            viewHolder.mTvText = convertView.findViewById(R.id.mTvText)
            viewHolder.mTvText!!.text = listData[position]
        return convertView
    }

    class ViewHolder {
        var mTvText: TextView? = null
    }

}