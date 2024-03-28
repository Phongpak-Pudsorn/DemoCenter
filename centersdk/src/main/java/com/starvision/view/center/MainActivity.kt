package com.starvision.view.center

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.data.AppPreferencesLogin
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.AdapterMenuTab
import com.starvision.view.center.adapter.AdapterPager
import com.starvision.view.center.info.TabInfo
import com.starvision.view.center.models.CenterModels
import com.starvision.view.stavisions.StarvisionFragment
import com.starvision.view.luckygamesdk.LuckyGameFragment
import com.starvision.view.playplay.PlayplayFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        binding.imgGoBack.setOnClickListener {
            finish()
        }
        executeData()
    }

    private fun executeData(){
        val services = ApiClient().getBaseLink(URL.BASE_URL_SDK,":443").create(Api::class.java)
        services.getCenter().enqueue(object :Callback<CenterModels>{
            override fun onResponse(call: Call<CenterModels>, response: Response<CenterModels>) {
                if (response.isSuccessful){
                    val list = response.body()
                    Log.e("list",list.toString())
                    for (i in list!!.data.PageCenter.indices){
                        tablist.add(TabInfo(list.data.PageCenter[i].MenuTitle))
                    }
                    binding.menuTab.apply {
                        adapter = AdapterMenuTab(this@MainActivity, tablist,object: AdapterMenuTab.TabClickListener{
                            override fun onTabClick(position: Int) {
                                binding.pager2.setCurrentItem(position,false)
                            }
                        })
                        layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
                    }
                }
            }

            override fun onFailure(call: Call<CenterModels>, t: Throwable) {

            }
        })
        setFragments()
        binding.pager2.adapter = AdapterPager(this,fragments)
        binding.pager2.isUserInputEnabled = false
        binding.pager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){

        })
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
    private fun setFragments(){
        fragments.add(StarvisionFragment())
        fragments.add(LuckyGameFragment())
        fragments.add(PlayplayFragment())

    }
}