package com.starvision.view.stavisions.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.ItemNewsBinding
import com.starvision.api.WebViewPage

class AdapterStarvision(private val mActivity : Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            holder.itemView.setOnClickListener {
                val intent = Intent(mActivity, WebViewPage::class.java)
                intent.putExtra("link", "https://www.google.com/")
                mActivity.startActivity(intent)
            }
        }

    }
}