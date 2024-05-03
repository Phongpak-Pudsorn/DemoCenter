package com.starvision.view.stavisions.playplay.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starvision.centersdk.databinding.ItemPlayplayBinding
import com.starvision.centersdk.databinding.ItemPlayplayHeaderBinding
import com.starvision.centersdk.databinding.PagePppBinding
import com.starvision.view.SvWebViewActivity

class SvAdapterPlayplay(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(val epBinding: PagePppBinding):RecyclerView.ViewHolder(epBinding.root)
    val handler = android.os.Handler(Looper.getMainLooper())
    var imageAdapter:SvAdapterPlayplayImage?=null
    init {
        imageAdapter= SvAdapterPlayplayImage(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = PagePppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            val runnable = Runnable {
                holder.epBinding.imageSlider.setCurrentItem(
                    holder.epBinding.imageSlider.currentItem + 1,
                    true
                )
            }
            holder.epBinding.imageSlider.adapter = SvAdapterPlayplayImage(context)
            holder.epBinding.imageSlider.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
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
                        0 -> holder.epBinding.imageSlider.setCurrentItem(
                            imageAdapter!!.itemCount - 2,
                            false
                        )
                        imageAdapter!!.itemCount - 1 -> holder.epBinding.imageSlider.setCurrentItem(
                            1,
                            false
                        )
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 5000)
                }
            })
            holder.epBinding.imageSlider.setCurrentItem(1, false)
            handler.postDelayed(runnable, 5000)
            holder.epBinding.dotTab.apply {

            }
            holder.epBinding.rvList.apply {
                adapter = SvAdapterPlayplayList()
                layoutManager = GridLayoutManager(context,3,RecyclerView.VERTICAL,false)
            }
        }

    }
}