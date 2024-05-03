package com.starvision.view.luckygamesdk.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageLuckyBinding
import com.starvision.centersdk.databinding.PagePppBinding
import kotlin.math.abs

class SvAdapterLucky(private val mActivity: Activity) : RecyclerView.Adapter<SvAdapterLucky.LuckyHolder>() {
    class LuckyHolder(val gameBinding: PageLuckyBinding):RecyclerView.ViewHolder(gameBinding.root)
    val handler = android.os.Handler(Looper.getMainLooper())
    var imageAdapter : SvAdapterGameBanner?=null
    init {
        imageAdapter= SvAdapterGameBanner(mActivity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LuckyHolder {
        val binding = PageLuckyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LuckyHolder(binding)
    }

    override fun getItemCount(): Int =1

    override fun onBindViewHolder(holder: LuckyHolder, position: Int) {
        val runnable = Runnable {
            holder.gameBinding.imageSlider.setCurrentItem(
                holder.gameBinding.imageSlider.currentItem + 1,
                true
            )
        }
        val nextItemVisiblePx = mActivity.resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = mActivity.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        val itemDecoration = HorizontalMarginItemDecoration(
            mActivity,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        holder.gameBinding.imageSlider.addItemDecoration(itemDecoration)
        holder.gameBinding.imageSlider.adapter = SvAdapterGameBanner(mActivity)
        holder.gameBinding.imageSlider.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                if (bannerList.size>=2) {
                    if (positionOffsetPixels != 0) {
                        return
                    }
                    when (position) {
                        0 -> holder.gameBinding.imageSlider.setCurrentItem(
                            imageAdapter!!.itemCount - 2,
                            false
                        )
                        imageAdapter!!.itemCount - 1 -> holder.gameBinding.imageSlider.setCurrentItem(
                            1,
                            false
                        )
                    }
//                }

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                if (bannerList.size>=2) {
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 5000)
//                }
            }
        })
        holder.gameBinding.imageSlider.clipToPadding = false
        holder.gameBinding.imageSlider.clipChildren = false
        holder.gameBinding.imageSlider.offscreenPageLimit = 1
        holder.gameBinding.imageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        holder.gameBinding.imageSlider.setPageTransformer(pageTransformer)
        holder.gameBinding.imageSlider.setCurrentItem(1, false)
        handler.postDelayed(runnable, 5000)

        holder.gameBinding.rvList.apply {
            adapter = SvAdapterGamePage(mActivity)
            layoutManager = GridLayoutManager(mActivity,2)
        }

    }
    inner class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }

}
