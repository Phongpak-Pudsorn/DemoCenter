package com.starvision.democenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.starvision.config.SvLogin
import com.starvision.data.SvConst
import com.starvision.democenter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var callback : OnBackPressedCallback? = null
    private lateinit var centerShow : CenterShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        centerShow = CenterShow(this)
//        SvConst.checkStatus()
//        SvConst.setClickListener(object : SvConst.CheckListener{
//            override fun onCheckIsSDK(isSdkSDK: Boolean) {
////                if(!isSdkSDK){
////                    binding.lnBtn.visibility = View.GONE
////                }
//            }
//
//            override fun onCheckIsReview(isReviewSDK: Boolean) {
//
//            }
//        })
        binding.btnCenter.setOnClickListener {
            centerShow.getMainCenter(this.packageName)
        }

        binding.imgPerson.setOnClickListener {
            centerShow.getMainCenter(this.packageName)
        }

        binding.btnLottoThai.setOnClickListener {
            centerShow.setShowFragmentLotto
        }
        binding.btnCheckLotto.setOnClickListener {
            centerShow.setShowFragmentCheckLotto
        }
        binding.btnGoldtoday.setOnClickListener {
            centerShow.setShowFragmentGold
        }
        binding.btnZodiac.setOnClickListener {
            centerShow.setShowFragmentZodiac
        }
        binding.btnExchange.setOnClickListener {
            centerShow.setShowFragmentExchange
        }
        binding.btnOil.setOnClickListener {
            centerShow.setShowFragmentOil
        }

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback!!)
    }

    override fun onResume() {
        super.onResume()
        if(SvLogin.isLogin){
            binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_green))
        }else{
            binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_black))
        }
//        if(Const.KEY_PREFS_FRAGMENT != "" ){
//            centerShow.checkFragment(centerShow.selectorFragment(Const.KEY_PREFS_FRAGMENT))
//        }
    }
}