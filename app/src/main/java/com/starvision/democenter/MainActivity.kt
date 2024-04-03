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
    private val appPrefe = AppPreferencesLogin
    private val subLottothaiPage = SubLottothaiPage()
    private val subSmileLottoPage = SubSmileLottoPage()
    private val subGoldToDayPage = SubGoldToDayPage()
    private val subZodiacPage = SubZodiacFragment()
    private val subExchangePage = SubExchangeFragment()
    private val subOilPage = SubOilFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setButtonCallback()
        binding.btnCenter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imgPerson.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLottoThai.setOnClickListener {
            setFragment(subLottothaiPage)
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnLottoCalendar.setOnClickListener {
            setFragment(subSmileLottoPage)
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnGoldtoday.setOnClickListener {
            setFragment(subGoldToDayPage)
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnZodiac.setOnClickListener {
            setFragment(subZodiacPage)
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnExchange.setOnClickListener {
            setFragment(subExchangePage)
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnOil.setOnClickListener {
            setFragment(subOilPage)
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.framelayoutFragment.visibility == View.VISIBLE){
                    binding.framelayoutFragment.removeAllViews()
                    binding.framelayoutFragment.visibility = View.GONE
                    binding.lnBtn.visibility = View.VISIBLE
                }else{
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback!!)
    }

    private fun setFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.framelayoutFragment.id, fragment)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        if(Login.isLogin){
            binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_green))
        }else{
            binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_black))
        }
    }
    private fun setButtonCallback(){
        subLottothaiPage.setClickListener(object : SubLottothaiPage.ClickListener{
            override fun onClickBack() {
                callback!!.handleOnBackPressed()
            }
        })
        subSmileLottoPage.setClickListener(object : SubSmileLottoPage.ClickListener{
            override fun onClickBack() {
                callback!!.handleOnBackPressed()
            }
        })
        subGoldToDayPage.setClickListener(object : SubGoldToDayPage.ClickListener{
            override fun onClickBack() {
                callback!!.handleOnBackPressed()
            }
        })
        subZodiacPage.setClickListener(object : SubZodiacFragment.ClickListener{
            override fun onClickBack() {
                callback!!.handleOnBackPressed()
            }
        })
        subOilPage.setClickListener(object : SubOilFragment.ClickListener{
            override fun onClickBack() {
                callback!!.handleOnBackPressed()
            }
        })
        subExchangePage.setClickListener(object : SubExchangeFragment.ClickListener{
            override fun onClickBack() {
                callback!!.handleOnBackPressed()
            }
        })
    }
}