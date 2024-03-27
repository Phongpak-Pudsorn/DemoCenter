package com.starvision.view.stavisions.adapter

import android.content.Context
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.ItemNewsBinding
import com.starvision.luckygamesdk.databinding.ItemNewsHeaderBinding
import com.starvision.luckygamesdk.databinding.ItemTabsBinding
import com.starvision.luckygamesdk.databinding.PageBannerAppsBinding
import com.starvision.view.stavisions.info.BannerInfo
import com.starvision.view.stavisions.info.NewsInfo
import java.util.logging.Handler

class AdapterStarvision(context:Context, val listNews:ArrayList<NewsInfo>, val bannerList:ArrayList<BannerInfo>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_NEWS = 1
    val VIEW_TYPE_BANNER = 2
    val VIEW_TYPE_NEWS_HEADER = 3
    var imageAdapter : AdapterImageSlide?=null
    var appAdapter : AdapterApps?=null
    var dotAdapter : AdapterDots?=null
    val handler = android.os.Handler(Looper.getMainLooper())
    init {
        imageAdapter = AdapterImageSlide(bannerList)
        appAdapter = AdapterApps(bannerList)
        dotAdapter = AdapterDots(1,bannerList)
    }
    class BannerHolder(val bannerBinding: PageBannerAppsBinding):RecyclerView.ViewHolder(bannerBinding.root)
    class NewsHolder(val newsBinding: ItemNewsBinding):RecyclerView.ViewHolder(newsBinding.root)
    class HeaderHolder(val headerBinding: ItemNewsHeaderBinding):RecyclerView.ViewHolder(headerBinding.root)
    override fun getItemViewType(position: Int): Int {
        if (listNews[position].newsappId=="banner"){
            return VIEW_TYPE_BANNER
        }else if (listNews[position].newsappId=="header"){
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
            holder.bannerBinding.imageSlider.adapter = imageAdapter
//            TabLayoutMediator(holder.bannerBinding.tabLayout,holder.bannerBinding.imageSlider){ tab, position ->
//                //Some implementation
//            }.attach()
            val runnable = Runnable {
                holder.bannerBinding.imageSlider.setCurrentItem(
                    holder.bannerBinding.imageSlider.currentItem + 1,
                    true
                )
            }
            holder.bannerBinding.dotTab.apply {
                adapter = dotAdapter
                layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            }
            holder.bannerBinding.imageSlider.setCurrentItem(1, false)
            handler.postDelayed(runnable, 3000)
            holder.bannerBinding.imageSlider.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
//                    if (state == ViewPager2.SCROLL_STATE_IDLE || state == ViewPager2.SCROLL_STATE_DRAGGING) {
//                        if (holder.bannerBinding.imageSlider.currentItem == 0)
//                            holder.bannerBinding.imageSlider.setCurrentItem(listNews.size - 2, false)
//                        else if (holder.bannerBinding.imageSlider.currentItem == listNews.size -1)
//                            holder.bannerBinding.imageSlider.setCurrentItem(1, false)
//                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (positionOffsetPixels != 0) {
                        return
                    }
                    when (position) {
                        0 ->  holder.bannerBinding.imageSlider.setCurrentItem(imageAdapter!!.itemCount - 2, false)
                        imageAdapter!!.itemCount - 1 ->  holder.bannerBinding.imageSlider.setCurrentItem(1, false)
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    val runnable = Runnable { holder.bannerBinding.imageSlider.currentItem = ++holder.bannerBinding.imageSlider.currentItem }
//                    if (position < (holder.bannerBinding.imageSlider.adapter?.itemCount ?: 0)) {
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 3000)
                    dotAdapter = AdapterDots(holder.bannerBinding.imageSlider.currentItem,bannerList)
                    holder.bannerBinding.dotTab.apply {
                        adapter = dotAdapter
                        layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
                    }
//                    }
                }
                })
            holder.bannerBinding.appList.apply {
                adapter = appAdapter
                layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            }
        }else if (holder is NewsHolder){
            holder.newsBinding.tvTitle.text = listNews[position].newsappTitle
        }

    }

}