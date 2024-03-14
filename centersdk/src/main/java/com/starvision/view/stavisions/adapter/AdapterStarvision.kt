package com.starvision.view.stavisions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starvision.luckygamesdk.databinding.ItemNewsBinding
import com.starvision.luckygamesdk.databinding.ItemNewsHeaderBinding
import com.starvision.luckygamesdk.databinding.ItemTabsBinding
import com.starvision.luckygamesdk.databinding.PageBannerAppsBinding
import com.starvision.view.stavisions.info.NewsInfo

class AdapterStarvision(context:Context,val listNews:ArrayList<NewsInfo>,val tabDots:AdapterDots,val imageSlide:AdapterImageSlide): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_NEWS = 1
    val VIEW_TYPE_BANNER = 2
    val VIEW_TYPE_NEWS_HEADER = 3
    class BannerHolder(val bannerBinding: PageBannerAppsBinding):RecyclerView.ViewHolder(bannerBinding.root)
    class NewsHolder(val newsBinding: ItemNewsBinding):RecyclerView.ViewHolder(newsBinding.root)
    class HeaderHolder(val headerBinding: ItemNewsHeaderBinding):RecyclerView.ViewHolder(headerBinding.root)
    override fun getItemViewType(position: Int): Int {
        if (listNews[position].id=="banner"){
            return VIEW_TYPE_BANNER
        }else if (listNews[position].id=="header"){
            return VIEW_TYPE_NEWS_HEADER
        }else{
            return VIEW_TYPE_NEWS
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==VIEW_TYPE_NEWS_HEADER){
            val binding = ItemNewsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderHolder(binding)
        }else if (viewType==VIEW_TYPE_BANNER){
            val binding = PageBannerAppsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BannerHolder(binding)
        }else {
            val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NewsHolder(binding)
        }
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderHolder){

        }else if (holder is BannerHolder){
            holder.bannerBinding.tabDots.apply {
                adapter = tabDots
                layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            }
            holder.bannerBinding.imageSlider.adapter = imageSlide
            holder.bannerBinding.imageSlider.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
                })
        }else if (holder is NewsHolder){
            holder.newsBinding.tvTitle.text = listNews[position].desc
        }

    }
}