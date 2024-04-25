package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.*
import com.starvision.data.SvAppPreferencesLogin
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.MainPageBinding
import com.starvision.view.center.adapter.SvAdapterMenuTab
import com.starvision.view.center.adapter.SvAdapterPager
import com.starvision.view.center.models.SvTabModels
import com.starvision.view.center.models.SvCenterModels
import com.starvision.view.center.sub.*
import com.starvision.view.login.SvLoginActivity
import com.starvision.view.stavisions.SvStarvisionFragment
import com.starvision.view.luckygamesdk.SvLuckyGameFragment
import com.starvision.view.playplay.SvPlayplayFragment
import com.starvision.view.stavisions.adapter.SvAdapterImageSlide
import kotlin.collections.ArrayList

class SvMainActivity: AppCompatActivity(),SvAdapterImageSlide.OnDataPass {
    private val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    private val appPrefs = SvAppPreferencesLogin
    private val TAG = javaClass.simpleName
    private var callback : OnBackPressedCallback? = null
    var tablist = ArrayList<SvTabModels>()
    var fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val extra = intent.getStringExtra("package")
        if (extra!=null){
            SvConst.appPackage = extra
        }

//        binding.lnCoin.visibility = View.VISIBLE
        if(!SvLogin.isLogin){
            val intent = Intent(this, SvLoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            executeData()
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

        val message = this.intent.getStringExtra("fragment")
        if(message != null){
            setSubPage(message)
        }

    }
    private fun executeData(){
        SvParamsData(object :SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val list = Gson().fromJson(body,SvCenterModels::class.java)
                if (list.code=="101") {
                    for (i in list!!.data.PageCenter.indices) {
//                        if (SvConst.isSdkSDK) {
//                            binding.lnCoin.visibility = View.INVISIBLE
//                        }
                        tablist.add(SvTabModels(list.data.PageCenter[i].MenuTitle))
                    }
                    binding.menuTab.apply {
                        adapter = SvAdapterMenuTab(
                            this@SvMainActivity,
                            tablist,
                            object : SvAdapterMenuTab.TabClickListener {
                                override fun onTabClick(position: Int) {
                                    binding.pager2.setCurrentItem(position, false)
                                }
                            })
                        layoutManager =
                            LinearLayoutManager(this@SvMainActivity, RecyclerView.HORIZONTAL, false)
                    }
                    if (tablist.size<1){
                        binding.menuTab.visibility = View.GONE
                    }else{
                        binding.menuTab.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t $t")
            }
        }).getLoadData(SvURL.BASE_URL_SDK,SvURL.URL_CENTER,"")
        binding.pager2.adapter = SvAdapterPager(this,fragments)
        binding.pager2.isUserInputEnabled = false
        binding.pager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){})
        binding.imgProfile.setOnClickListener {
            binding.imgProfile.isEnabled = false
            val dialogProfile = SvProfileDialogFragment()
            dialogProfile.setClickListener(object : SvProfileDialogFragment.ClickListener{
                override fun onLogout() {
                    finish()
                }

                override fun onCancel() {
                    binding.imgProfile.isEnabled = true
                }

                override fun onDelete() {
                    val delete = SvDeleteAccountDialogFragment()
                    delete.setClickListener(object : SvDeleteAccountDialogFragment.ClickListener{
                        override fun onClickBack() {
                            this@SvMainActivity.finish()
                        }
                    })
                    delete.show(supportFragmentManager,"delete")
                }
            })
            dialogProfile.show(supportFragmentManager,"")
        }
        binding.tvUsername.text = SvLogin.Name
        binding.tvCoinNum.text = " "+SvLogin.Coin
        Glide.with(this@SvMainActivity).load(SvLogin.Avatar).into(binding.imgProfile)
        SvConst.loge(TAG,"Login.Avatar : "+SvLogin.Avatar)
    }
    private fun setFragments(){
        fragments.add(SvStarvisionFragment())
        fragments.add(SvLuckyGameFragment())
        fragments.add(SvPlayplayFragment())

    }
    private fun setSubPage(packName:String){
        if (packName==getString(R.string.oil_package)){
            SvSubOilFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.gold_package)){
            SvSubGoldToDayFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.exchange_package)){
            SvSubExchangeFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.zodiac_package)){
            SvSubZodiacFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.lucky_package)){
            SvSubLottothaiFragment().show(supportFragmentManager,"")
        }else if (packName==getString(R.string.lottery_package)){
            SvSubSmileLottoFragment().show(supportFragmentManager,"")
        }
    }

    override fun passData(packName: String) {
        if (packName!=""){
            setSubPage(packName)
        }
    }

    override fun onResume() {
        super.onResume()
        SvConst.clickAble = true
    }

    override fun onDestroy() {
        callback!!.remove()
        super.onDestroy()
    }
}