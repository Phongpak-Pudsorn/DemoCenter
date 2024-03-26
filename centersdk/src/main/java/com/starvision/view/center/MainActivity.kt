package com.starvision.view.center

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starvision.data.AppPreferencesLogin
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.AdapterMenuTab
import com.starvision.view.center.adapter.AdapterPager
import com.starvision.view.center.info.TabInfo
import com.starvision.view.stavisions.StarvisionFragment
import com.starvision.view.luckygamesdk.LuckyGameFragment
import com.starvision.view.playplay.PlayplayFragment

class MainActivity: AppCompatActivity() {
    private val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    private val appPrefs = AppPreferencesLogin
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

        })
        binding.menuTab.apply {
            adapter = AdapterMenuTab(this@MainActivity, tablist,object: AdapterMenuTab.TabClickListener{
                override fun onTabClick(position: Int) {
                    binding.pager2.setCurrentItem(position,false)
                }
            })
            layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
        }
        binding.tvCoinNum.text = "0"
        binding.tvUsername.text = appPrefs.getPreferences(this@MainActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,"") as String
        binding.imgProfile.setOnClickListener {
            val dialogProfile = ProfileDialogFragment()
            dialogProfile.setClickListener(object : ProfileDialogFragment.ClickListener{
                override fun onLogout() {
                    finish()
                }
            })
            dialogProfile.show(supportFragmentManager,"")
        }
    }
    private fun setTab(): ArrayList<TabInfo> {
        tablist.clear()
        tablist.add(TabInfo("Stavision",false))
        tablist.add(TabInfo("Lucky Game",false))
        tablist.add(TabInfo("Playplay+",false))

        return tablist
    }
    private fun setFragments(){
        fragments.add(StarvisionFragment())
        fragments.add(LuckyGameFragment())
        fragments.add(PlayplayFragment())

    }
}