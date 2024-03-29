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
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.starvision.api.URL
import com.starvision.config.*
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.AdapterMenuTab
import com.starvision.view.center.adapter.AdapterPager
import com.starvision.view.center.info.TabInfo
import com.starvision.view.center.models.CenterModels
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.LoginActivity
import com.starvision.view.stavisions.StarvisionFragment
import com.starvision.view.luckygamesdk.LuckyGameFragment
import com.starvision.view.luckygamesdk.models.LuckyGameModels
import com.starvision.view.playplay.PlayplayFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity: AppCompatActivity() {
    private val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    private val appPrefs = AppPreferencesLogin
    private val TAG = javaClass.simpleName
    var tablist = ArrayList<TabInfo>()
    var fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        if(!Login.isLogin){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadApiCenter()
        tablist = setTab()
        setFragments()
        binding.imgGoBack.setOnClickListener {
            finish()
        }
        binding.pager2.adapter = AdapterPager(this,fragments)
        binding.pager2.isUserInputEnabled = false
        binding.pager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){})
        binding.menuTab.apply {
            adapter = AdapterMenuTab(this@MainActivity, tablist,object: AdapterMenuTab.TabClickListener{
                override fun onTabClick(position: Int) {
                    binding.pager2.setCurrentItem(position,false)
                }
            })
            layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
        }
        binding.imgProfile.setOnClickListener {
            binding.imgProfile.isEnabled = false
            val dialogProfile = ProfileDialogFragment()
            dialogProfile.setClickListener(object : ProfileDialogFragment.ClickListener{
                override fun onLogout() {
                    finish()
                }

                override fun onCancel() {
                    binding.imgProfile.isEnabled = true
                }
            })
            dialogProfile.show(supportFragmentManager,"")
        }
        binding.tvUsername.text = Login.getName
        binding.tvCoinNum.text = Login.getCoin
        Glide.with(this).load(Login.getAvatar).into(binding.imgProfile)

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

    private fun loadApiCenter(){
        ParamsData(object : ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataModel = Gson().fromJson(body,CenterModels::class.java)
                    for (i in dataModel.data.PageCenter.indices) {
                        Const.loge(TAG,"dataModel : "+dataModel.data.PageCenter[i])

                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailed(t: Throwable) {
                t.printStackTrace()
            }
        }).getLoadData(URL.BASE_URL_SDK,URL.URL_CENTER,"")
    }

}