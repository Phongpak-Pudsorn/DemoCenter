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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnCenter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imgPerson.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLottoThai.setOnClickListener {
            setFragment(SubLottothaiPage())
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnLottoCalendar.setOnClickListener {
            setFragment(SubSmileLottoPage())
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnGoldtoday.setOnClickListener {
            setFragment(SubGoldToDayPage())
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnZodiac.setOnClickListener {
            setFragment(SubZodiacFragment())
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnExchange.setOnClickListener {
            setFragment(SubExchangeFragment())
            binding.framelayoutFragment.visibility = View.VISIBLE
            binding.lnBtn.visibility = View.GONE
        }
        binding.btnOil.setOnClickListener {
            setFragment(SubOilFragment())
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
}