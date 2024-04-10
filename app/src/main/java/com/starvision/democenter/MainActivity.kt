package com.starvision.democenter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.starvision.api.URL
import com.starvision.config.Login
import com.starvision.config.ParamsData
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.democenter.databinding.ActivityMainBinding
import com.starvision.view.center.MainActivity
import com.starvision.view.center.sub.*
import com.starvision.view.login.LoginActivity
import com.starvision.view.login.models.LoginModels

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var callback : OnBackPressedCallback? = null
    private lateinit var centerShow : CenterShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        centerShow = CenterShow(this)

        binding.btnCenter.setOnClickListener {
            centerShow.getMainCenter()
        }

        binding.imgPerson.setOnClickListener {
            centerShow.getMainCenter()
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
        if(Login.isLogin){
            binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_green))
        }else{
            binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_black))
        }
//        if(Const.KEY_PREFS_FRAGMENT != "" ){
//            centerShow.checkFragment(centerShow.selectorFragment(Const.KEY_PREFS_FRAGMENT))
//        }
    }
}