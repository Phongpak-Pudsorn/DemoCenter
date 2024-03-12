package com.starvision.democenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.starvision.democenter.databinding.ActivityMainBinding
import com.starvision.view.center.view.MainPage
import com.starvision.view.login.view.LoginPage

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnCenter.setOnClickListener {
            val intent = Intent(this,MainPage::class.java)
            startActivity(intent)
        }

        binding.imgPerson.setOnClickListener {
            val intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
        }
    }

//    private fun setFragment (fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
////            .setCustomAnimations(androidx.appcompat.R.anim.abc_slide_in_top,
////            androidx.appcompat.R.anim.abc_slide_out_bottom)
//            .replace(binding.framelayoutFragment.id, fragment)
//            .commit()
//    }
}