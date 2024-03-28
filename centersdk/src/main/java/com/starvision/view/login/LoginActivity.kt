package com.starvision.view.login

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
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.starvision.api.*
import com.starvision.config.CryptoHandler
import com.starvision.config.MD5
import com.starvision.config.ParamsData
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageLoginBinding
import com.starvision.view.center.MainActivity
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.models.LoginModels
import okhttp3.ResponseBody
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class LoginActivity : AppCompatActivity() {
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

        if(appPrefe.getPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true) == true){
            val user = appPrefe.getPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,"")
            val password = appPrefe.getPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,"")
            binding.editUsername.text = Editable.Factory.getInstance().newEditable(user.toString())
            binding.editPassword.text = Editable.Factory.getInstance().newEditable(password.toString())
            binding.checkboxRememberPass.isChecked = true
        }

        binding.tvRegister.setOnClickListener {
            val registerFragment = RegisterFragment(bm)
            registerFragment.setClickListener(object : RegisterFragment.ClickListener {
                override fun onClose() {
                    toggle()
                }

                override fun onSuccess() {
                    val user = appPrefe.getPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,"")
                    val password = appPrefe.getPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,"")
                    binding.editUsername.text = Editable.Factory.getInstance().newEditable(user.toString())
                    binding.editPassword.text = Editable.Factory.getInstance().newEditable(password.toString())
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
            Toast.makeText(this,getString(R.string.text_alert_user_min),Toast.LENGTH_SHORT).show()
        }else if(binding.editPassword.length() < 6){
            Toast.makeText(this,getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT).show()
        }else{
            val password = MD5.CMD5(binding.editPassword.text.toString())
            val imei = Const.getUUID(this)
            val platform = "Android "+Build.VERSION.SDK_INT
            val model = getDeviceName()
            val ChannelId = "StarVision"
            val phonenumber = ""
            val acc_name = binding.editUsername.text.toString().lowercase(Locale.getDefault())
            val account_type = "s1"
            val hashMap = HashMap<String?,String?>()
            hashMap["password"] = password
            hashMap["imei"] = imei
            hashMap["platform"] = platform
            hashMap["model"] = model
            hashMap["ChannelId"] = ChannelId
            hashMap["phonenumber"] = phonenumber
            hashMap["acc_name"] = acc_name
            hashMap["account_type"] = account_type

            Const.loge(TAG, "hashmap : $hashMap")

            ParamsData(object : ParamsData.PostLoadListener{
                override fun onSuccess(body : String) {
                    binding.cvLogin.isEnabled = true
                    binding.progressBar2.visibility = View.GONE
                    try {
                        val jSon = Gson().fromJson(body,LoginModels::class.java)
                        if(jSon.message == "success"){
                            if(binding.checkboxRememberPass.isChecked){
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true)
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,binding.editUsername.text.toString())
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,binding.editPassword.text.toString())
                            }else{
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,false)
                            }
                            val timeStamp : String = SimpleDateFormat("HHmmssddMMyyyy").format(Date())
                            val idx = CryptoHandler().encrypt(jSon.idx,Const.AES_KEY,"0000000000000000")
                            val ts =  CryptoHandler().encrypt(timeStamp ,Const.AES_KEY,"0000000000000000")
                            val sign = MD5.CMD5("Starvision|${jSon.idx}|CheckProfile|$timeStamp")
                            val hashMaps = java.util.HashMap<String?, String?>()
                            hashMaps["idx"] = idx
                            hashMaps["ts"] = ts
                            hashMaps["sign"] = sign
                            Const.loge(TAG, "params : $hashMaps")

                            ParamsData(object : ParamsData.PostLoadListener{
                                override fun onSuccess(body: String) {
                                    try {
                                        val jSonPro = Gson().fromJson(body, ProfileModels::class.java)
//                                        Const.loge(TAG," "+jSonPro.message)
//                                        Const.loge(TAG," "+jSonPro.code)
//                                        Const.loge(TAG," "+jSonPro.data!!.name)
//                                        Const.loge(TAG," "+jSonPro.data.avatar)
//                                        Const.loge(TAG," "+jSonPro.data.coin)
                                        appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_NAME, jSonPro.data!!.name!!.toString())
                                        appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_AVATAR,jSonPro.data.avatar!!.toString())
                                        appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_COIN,jSonPro.data.coin!!.toString())

                                        Const.loge(TAG," "+appPrefe.getPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_NAME,""))
                                        Const.loge(TAG," "+appPrefe.getPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_AVATAR,""))
                                        Const.loge(TAG," "+appPrefe.getPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_COIN,""))
//
                                    }catch (e : Exception){
                                        e.printStackTrace()
                                    }
                                }

                                override fun onFailed(t: Throwable) {
                                    Const.loge(TAG,"t : $t")
                                }
                            }).postLoadData(URL.BASE_URL_SDK,URL.URL_PROFILE,"",hashMaps)
                            appPrefe.setPreferences(this@LoginActivity, AppPreferencesLogin.KEY_PREFS_USERID,jSon.userid)
                            appPrefe.setPreferences(this@LoginActivity, AppPreferencesLogin.KEY_PREFS_SKEY,jSon.SKey!!)
                            appPrefe.setPreferences(this@LoginActivity, AppPreferencesLogin.KEY_PREFS_IDX,jSon.idx!!)
//                            appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_LOGIN,true)
                            Const.KEY_PREFS_LOGIN = true
                            Toast.makeText(this@LoginActivity,jSon.message,Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@LoginActivity,jSon.message,Toast.LENGTH_SHORT).show()
                        }
                    }catch (e : Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailed(t: Throwable) {
                    binding.cvLogin.isEnabled = true
                    binding.progressBar2.visibility = View.GONE
                    Const.loge(TAG,"t : $t")
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

    private fun loadCheckProfile(){
    }
}