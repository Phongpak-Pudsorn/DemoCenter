package com.starvision.democenter

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.starvision.config.Login
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

        binding.btnCenter.setOnClickListener {
            centerShow.getMainCenter()
        }

        binding.imgPerson.setOnClickListener {
            centerShow.getMainCenter()
        }
        binding.btnLottoThai.setOnClickListener {
            centerShow.setShowFragmentLotto()
        }
        binding.btnLottoCalendar.setOnClickListener {

        }
        binding.btnGoldtoday.setOnClickListener {
            centerShow.setShowFragmentGold
        }
        binding.btnZodiac.setOnClickListener {

        }
        binding.btnExchange.setOnClickListener {

        }
        binding.btnOil.setOnClickListener {

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
    }
}