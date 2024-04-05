package com.starvision.view.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.starvision.api.*
import com.starvision.config.CryptoHandler
import com.starvision.config.Login
import com.starvision.config.MD5
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageLoginBinding
import com.starvision.view.WebViewActivity
import com.starvision.view.center.MainActivity
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.center.sub.*
import com.starvision.view.login.models.LoginModels
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val binding : PageLoginBinding by lazy { PageLoginBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private var callback : OnBackPressedCallback? = null
    private val TAG = javaClass.simpleName
    private val bundle = Intent()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bm = getBitmapFromAsset("logo_starvision.png")
        binding.imgLogo.setImageBitmap(bm)

        if(Login.isRememberCheck){
            val user = Login.UserName
            val password = Login.Password
            binding.editUsername.text = Editable.Factory.getInstance().newEditable(user)
            binding.editPassword.text = Editable.Factory.getInstance().newEditable(password)
            binding.checkboxRememberPass.isChecked = true
        }

        binding.tvRegister.setOnClickListener {
            binding.tvRegister.isEnabled = false
            handler.postDelayed({binding.tvRegister.isEnabled = true},1000)
            val registerFragment = RegisterFragment(bm)
            registerFragment.setClickListener(object : RegisterFragment.ClickListener {
                override fun onClose() {
                    toggle()
                }

                override fun onSuccess() {
                    val user = Login.UserName
                    val password = Login.Password
                    binding.editUsername.text = Editable.Factory.getInstance().newEditable(user)
                    binding.editPassword.text = Editable.Factory.getInstance().newEditable(password)
                    binding.checkboxRememberPass.isChecked = true
                    onLogin()
                }
            })
            toggle()
            setFragment(registerFragment)
        }
        binding.tvForgot.setOnClickListener {
            val forgotFragment = ForgotFragment(bm)
            forgotFragment.setCloseListener(object : ForgotFragment.CloseListener {
                override fun onClose() {
                    toggle()
                }
            })
            toggle()
            setFragment(forgotFragment)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvPolicy.setOnClickListener {
            binding.tvPolicy.isEnabled = false
            handler.postDelayed({ binding.tvPolicy.isEnabled = true },1000)
            WebViewPolicyDialogFragment().show(supportFragmentManager,"policy")
        }
        binding.cvLogin.setOnClickListener {
            binding.cvLogin.isEnabled = false
            binding.progressBar2.visibility = View.VISIBLE
            onLogin()
        }

        binding.seePassword.setOnClickListener {
            if(binding.seePassword.drawable.constantState!! == this.getDrawable(R.drawable.baseline_eye_visibility_off_24)!!.constantState){
                binding.seePassword.setImageDrawable(getDrawable(R.drawable.baseline_eye_24_grey))
                binding.editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.seePassword.setImageDrawable(getDrawable(R.drawable.baseline_eye_visibility_off_24))
                binding.editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
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

    private fun onLogin(){
        binding.cvLogin.isEnabled = false
        handler.postDelayed({ binding.cvLogin.isEnabled = true },1000)
        if(binding.editUsername.length() < 6){
            val toast = Toast.makeText(this,getString(R.string.text_alert_user_min),Toast.LENGTH_SHORT)
            toast.show()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({ toast.cancel() }, 1000)
            binding.progressBar2.visibility = View.GONE
        }else if(binding.editPassword.length() < 6){
            val toast = Toast.makeText(this,getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT)
            toast.show()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({ toast.cancel() }, 1000)
            binding.progressBar2.visibility = View.GONE
        }else{
            val password = MD5.CMD5(binding.editPassword.text.toString())
            val ChannelId = "StarVision"
            val phonenumber = ""
            val acc_name = binding.editUsername.text.toString().lowercase(Locale.getDefault())
            val account_type = "s1"
            val hashMap = ParamUtil.ParamsUid

            hashMap["password"] = password
            hashMap["ChannelId"] = ChannelId
            hashMap["phonenumber"] = phonenumber
            hashMap["acc_name"] = acc_name
            hashMap["account_type"] = account_type

            Const.loge(TAG, "hashmap : $hashMap")

            ParamsData(object : ParamsData.PostLoadListener{
                override fun onSuccess(body : String) {
                    try {
                        val jSon = Gson().fromJson(body,LoginModels::class.java)
                        if(jSon.message == "success"){
                            if(binding.checkboxRememberPass.isChecked){
                                Login.isRememberCheck = true
                                Login.UserName = binding.editUsername.text.toString()
                                Login.Password = binding.editPassword.text.toString()
                            }else{
                                Login.isRememberCheck = false
                            }

                            val timeStamp : String = SimpleDateFormat("HHmmssddMMyyyy").format(Date())
                            val sign = MD5.CMD5("Starvision|${jSon.idx}|CheckProfile|$timeStamp")
                            val hashMaps = ParamUtil.ParamsUid
                            hashMaps["sign"] = sign
                            Const.loge(TAG, "params : $hashMaps")

                            ParamsData(object : ParamsData.PostLoadListener{
                                override fun onSuccess(body: String) {
                                    try {
                                        binding.cvLogin.isEnabled = true
                                        binding.progressBar2.visibility = View.GONE
                                        val jSonPro = Gson().fromJson(body, ProfileModels::class.java)
                                        Login.Name = jSonPro.data!!.name!!.toString()
                                        Login.Avatar = jSonPro.data.avatar!!.toString()
                                        Login.Coin = jSonPro.data.coin!!.toString()

                                        val message = intent.getStringExtra("fragment")
                                        if(message != null){
                                            Login.checkFragment = message
                                            finish()
                                        }else{
                                            val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        Toast.makeText(this@LoginActivity,jSon.message,Toast.LENGTH_SHORT).show()
                                    }catch (e : Exception){
                                        Toast.makeText(this@LoginActivity,jSon.message,Toast.LENGTH_SHORT).show()
                                        e.printStackTrace()
                                    }
                                }
                                override fun onFailed(t: Throwable) {
                                    Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_SHORT).show()
                                    Const.loge(TAG,"t : $t")
                                }
                            }).postLoadData(URL.BASE_URL_SDK,URL.URL_PROFILE,"",hashMaps)
                            Login.UserID = jSon.userid
                            Login.IDX = jSon.idx!!
                            Login.isLogin = true
                        }else{
                            Toast.makeText(this@LoginActivity,jSon.message,Toast.LENGTH_SHORT).show()
                            binding.cvLogin.isEnabled = true
                            binding.progressBar2.visibility = View.GONE
                        }
                    }catch (e : Exception) {
                        Toast.makeText(this@LoginActivity,e.message,Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
                override fun onFailed(t: Throwable) {
                    binding.cvLogin.isEnabled = true
                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_SHORT).show()
                }
            }).postLoadData(URL.BASE_URL_SDK,URL.URL_LOGIN,"",hashMap)

        }
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
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase(Locale.getDefault())
                .startsWith(manufacturer.lowercase(Locale.getDefault()))
        ) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}