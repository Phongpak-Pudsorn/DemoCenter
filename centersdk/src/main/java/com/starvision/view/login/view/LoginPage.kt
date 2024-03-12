package com.starvision.view.login.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageLoginBinding
import com.starvision.view.luckygamesdk.view.LuckyGamePage


class LoginPage : AppCompatActivity() {
    private val binding : PageLoginBinding by lazy { PageLoginBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private var callback : OnBackPressedCallback? = null
    private var appPrefe = AppPreferencesLogin
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val bm = getBitmapFromAsset("logo_starvision.png")
        binding.imgLogo.setImageBitmap(bm)
        binding.tvRegister.setOnClickListener {
            val registerPage = RegisterPage(bm)
            registerPage.setCloseListener(object : RegisterPage.CloseListener{
                override fun onClose() {
                    toggle()
                }
            })
            toggle()
            setFragment(registerPage)
        }
        binding.tvForgot.setOnClickListener {
            val forgotPage = ForgotPage(bm)
            forgotPage.setCloseListener(object : ForgotPage.CloseListener{
                override fun onClose() {
                    toggle()
                }
            })
            toggle()
            setFragment(forgotPage)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvPolicy.setOnClickListener {
            binding.tvPolicy.isEnabled = false
            handler.postDelayed({ binding.tvPolicy.isEnabled = true },1000)
            WebViewPage().show(supportFragmentManager,"policy")
        }
        binding.cvLogin.setOnClickListener {
            binding.cvLogin.isEnabled = false
            handler.postDelayed({ binding.cvLogin.isEnabled = true },1000)
            if(binding.editUsername.length() <= 6){
                Toast.makeText(this,getString(R.string.text_alert_user_min),Toast.LENGTH_SHORT).show()
            }else if(binding.editPassword.length() <= 6){
                Toast.makeText(this,getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT).show()
            }else{
                if(binding.checkboxRememberPass.isChecked){
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true)
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,binding.editUsername.text.toString())
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,binding.editPassword.text.toString())
                }else{
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,false)
                }
                //set ไปหน้าต่อไป
                val luckyGamePage = LuckyGamePage()
                toggle()
                setFragment(luckyGamePage)
            }
        }

        if(appPrefe.getPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true) == true){
            val user = appPrefe.getPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,"")
            val password = appPrefe.getPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,"")
            binding.editUsername.text = Editable.Factory.getInstance().newEditable(user.toString())
            binding.editPassword.text = Editable.Factory.getInstance().newEditable(password.toString())
            binding.checkboxRememberPass.isChecked = true
        }

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.frameFragment.visibility == View.VISIBLE){
                    toggle()
                }else{
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback!!)

    }

    private fun getBitmapFromAsset(strPic: String): Bitmap {
        val assetManager = assets
        val iStr = assetManager.open(strPic)
        return BitmapFactory.decodeStream(iStr)
    }

    private fun setFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameFragment.id, fragment)
            .commit()
    }
    private fun toggle() {
        val transition = Slide(Gravity.BOTTOM)
        transition.duration = 200
        transition.addTarget(binding.frameFragment)
        TransitionManager.beginDelayedTransition(binding.frameFragment, transition)
        if(binding.frameFragment.visibility == View.VISIBLE){
            binding.frameFragment.visibility = View.GONE
            handler.postDelayed({binding.lnTotal.visibility = View.VISIBLE},200)
        }else{
            handler.postDelayed({binding.lnTotal.visibility = View.GONE},150)
            binding.frameFragment.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        callback!!.remove()
        super.onDestroy()
    }
}