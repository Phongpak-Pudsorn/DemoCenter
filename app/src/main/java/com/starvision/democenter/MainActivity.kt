package com.starvision.democenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.starvision.democenter.databinding.ActivityMainBinding
import com.starvision.view.center.sub.SubLottothaiPage
import com.starvision.view.center.sub.SubSmileLottoPage
import com.starvision.view.center.view.MainPage
import com.starvision.view.login.view.LoginPage

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var callback : OnBackPressedCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        //เช็ค Login ถ้า login แล้วให้ขึ้นรูปเขียว
//    if(Login == true){
//        binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_green))
//    }else{
//        binding.imgPerson.setImageDrawable(getDrawable(R.drawable.baseline_person_24_black))
//    }


        binding.btnCenter.setOnClickListener {
            val intent = Intent(this,MainPage::class.java)
            startActivity(intent)
        }

        binding.imgPerson.setOnClickListener {
            // REAL
//            val intent = Intent(this,MainPage::class.java)
//            startActivity(intent)
            // TEST LOGIN
            val intent = Intent(this,LoginPage::class.java)
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

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.framelayoutFragment.visibility == View.VISIBLE){
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

}