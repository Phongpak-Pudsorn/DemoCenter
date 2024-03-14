package com.starvision.view.center.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.AdapterMenuTab
import com.starvision.view.center.adapter.AdapterPager
import com.starvision.view.center.info.TabInfo
import com.starvision.view.luckygamesdk.view.LuckyGamePage
import com.starvision.view.playplay.view.PlayplayPage
import com.starvision.view.stavisions.view.ImageTestPage
import com.starvision.view.stavisions.view.StarvisionPage

class MainPage: AppCompatActivity() {
    private val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    var tablist = ArrayList<TabInfo>()
    var fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        //ทำเช็ค login

        tablist = setTab()
        setFragments()
        binding.imgGoBack.setOnClickListener {
            finish()
        }
        binding.pager2.adapter = AdapterPager(this,fragments)
        binding.pager2.isUserInputEnabled = false
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
            adapter = AdapterMenuTab(this@MainPage, tablist,object: AdapterMenuTab.TabClickListener{
                override fun onTabClick(position: Int) {
                    binding.pager2.setCurrentItem(position,false)
                }
            })
            layoutManager = LinearLayoutManager(this@MainPage,RecyclerView.HORIZONTAL,false)
        }
        binding.tvCoinNum.text = "0"
        binding.tvUsername.text = "NoFace"
        binding.imgProfile.setOnClickListener { ProfilePage().show(supportFragmentManager,"") }
    }
    private fun setTab(): ArrayList<TabInfo> {
        tablist.clear()
        tablist.add(TabInfo("Stavision",false))
        tablist.add(TabInfo("Lucky Game",false))
        tablist.add(TabInfo("Playplay+",false))
//        tablist.add(TabInfo("imageTest",false))
        return tablist
    }
    private fun setFragments(){
        fragments.add(StarvisionPage())
        fragments.add(LuckyGamePage())
        fragments.add(PlayplayPage())
//        fragments.add(ImageTestPage())
    }
}