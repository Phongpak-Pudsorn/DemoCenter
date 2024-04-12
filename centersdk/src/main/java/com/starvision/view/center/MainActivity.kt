package com.starvision.view.center

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback

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
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.BuildConfig
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.AdapterMenuTab
import com.starvision.view.center.adapter.AdapterPager
import com.starvision.view.center.info.TabInfo
import com.starvision.view.center.models.CenterModels
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.center.sub.*
import com.starvision.view.login.LoginActivity
import com.starvision.view.stavisions.StarvisionFragment
import com.starvision.view.luckygamesdk.LuckyGameFragment
import com.starvision.view.luckygamesdk.models.LuckyGameModels
import com.starvision.view.playplay.PlayplayFragment
import com.starvision.view.stavisions.adapter.AdapterImageSlide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity: AppCompatActivity(),AdapterImageSlide.OnDataPass {
    private val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    private val appPrefs = AppPreferencesLogin
    private val TAG = javaClass.simpleName
    private var callback : OnBackPressedCallback? = null
    private val subLottothaiPage = SubLottothaiPage()
    private val subSmileLottoPage = SubSmileLottoPage()
    private val subGoldToDayPage = SubGoldToDayPage()
    private val subZodiacPage = SubZodiacFragment()
    private val subExchangePage = SubExchangeFragment()
    private val subOilPage = SubOilFragment()
    var tablist = ArrayList<TabInfo>()
    var fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val extra = intent.getStringExtra("package")
        if (extra!=null){
            Const.appPackage = extra
        }

        if(!Login.isLogin){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.fmSub.visibility == View.VISIBLE){
                    binding.fmSub.visibility = View.GONE
                    binding.llMain.visibility = View.VISIBLE
                }else{
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback!!)
        setFragments()
        binding.imgGoBack.setOnClickListener {
            finish()
        }
        executeData()

        val message = this.intent.getStringExtra("fragment")
        if(message != null){
            setSubPage(message)
        }

    }
    private fun executeData(){
        ParamsData(object :ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val list = Gson().fromJson(body,CenterModels::class.java)
                if (list.code=="101") {
                    for (i in list!!.data.PageCenter.indices) {
                        tablist.add(TabInfo(list.data.PageCenter[i].MenuTitle))
//                    packageName = list.data.PageCenter[i].IconApp
                    }
                    binding.menuTab.apply {
                        adapter = AdapterMenuTab(
                            this@MainActivity,
                            tablist,
                            object : AdapterMenuTab.TabClickListener {
                                override fun onTabClick(position: Int) {
                                    binding.pager2.setCurrentItem(position, false)
                                }
                            })
                        layoutManager =
                            LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                    }
                    if (tablist.size<=1){
                        binding.menuTab.visibility = View.GONE
                    }else{
                        binding.menuTab.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t $t")
            }
        }).getLoadData(URL.BASE_URL_SDK,URL.URL_CENTER,"")
        binding.pager2.adapter = AdapterPager(this,fragments)
        binding.pager2.isUserInputEnabled = false
        binding.pager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){})
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

                override fun onDelete() {
                    val delete = DeleteAccountDialogFragment()
                    delete.setClickListener(object : DeleteAccountDialogFragment.ClickListener{
                        override fun onClickBack() {
                            this@MainActivity.finish()
                        }
                    })
                    delete.show(supportFragmentManager,"delete")
                }
            })
            dialogProfile.show(supportFragmentManager,"")
        }
        binding.tvUsername.text = Login.Name
        binding.tvCoinNum.text = Login.Coin
        Glide.with(this@MainActivity).load(Login.Avatar).into(binding.imgProfile)
        Const.loge(TAG,"Login.Avatar : "+Login.Avatar)
    }
    private fun setFragments(){
        fragments.add(StarvisionFragment())
        fragments.add(LuckyGameFragment())
        fragments.add(PlayplayFragment())

    }
    private fun setSubPage(packName:String){
        if (packName==getString(R.string.oil_package)){
            SubOilFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.gold_package)){
            SubGoldToDayPage().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.exchange_package)){
            SubExchangeFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.zodiac_package)){
            SubZodiacFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.lucky_package)){
            SubLottothaiPage().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.lottery_package)){
            SubSmileLottoPage().show(supportFragmentManager,"")
        }
    }

    override fun passData(packName: String) {
        if (packName!=""){
            setSubPage(packName)
        }
    }

    override fun onResume() {
        super.onResume()
        Const.clickAble = true
    }

    override fun onDestroy() {
        callback!!.remove()
        super.onDestroy()
    }
}