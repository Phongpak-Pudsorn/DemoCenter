package com.starvision.view.center.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.AdapterMenuTab
import com.starvision.view.center.adapter.AdapterPager
import com.starvision.view.luckygamesdk.view.LuckyGamePage
import com.starvision.view.playplay.view.PlayplayPage
import com.starvision.view.stavisions.view.StarvisionPage

class MainPage: AppCompatActivity() {
    private val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    var fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val tabList = mutableListOf<Pair<String,Boolean>>()
        tabList.add(Pair("Stavision",true))
        tabList.add(Pair("Lucky Game",false))
        tabList.add(Pair("Playplay+",false))
        fragments.add(StarvisionPage())
        fragments.add(LuckyGamePage())
        fragments.add(PlayplayPage())
        binding.imgGoBack.setOnClickListener {
            finish()
        }
        binding.pager2.adapter = AdapterPager(this,fragments)
        binding.pager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
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
        binding.menuTab.apply {
            adapter = AdapterMenuTab(this@MainPage,tabList,object: AdapterMenuTab.tabClickLiatener{
                override fun onTabClick(position: Int) {
                    binding.pager2.setCurrentItem(position,false)
                }
            })
            layoutManager = LinearLayoutManager(this@MainPage,RecyclerView.HORIZONTAL,false)
        }
        binding.tvCoinNum.text = "0"
        binding.tvUsername.text = "NoFace"
    }
}