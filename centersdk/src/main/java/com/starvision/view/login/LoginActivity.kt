package com.starvision.view.login

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.text.Editable
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.starvision.api.*
import com.starvision.config.AESHelper
import com.starvision.config.MD5
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageLoginBinding
import com.starvision.view.center.MainActivity
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.models.LoginModels
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
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
            onLogin()
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

            Const.loge(TAG,"params : $hashMap")
            val apiService = ApiClient().getBaseLink(URL.BASE_URL_SDK,"").create(Api::class.java)
            apiService.postRequest("/login/api/login_star.php",hashMap).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Const.loge(TAG,"onResponse url :"+call.request().url)
                    val jSon = Gson().fromJson(response.body()!!.string(), LoginModels::class.java)
                    Const.loge(TAG,"onResponse jSon :"+jSon.message)
                    try {
                        if(jSon.message == "success"){
                            if(binding.checkboxRememberPass.isChecked){
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true)
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,binding.editUsername.text.toString())
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,binding.editPassword.text.toString())
                            }else{
                                appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,false)
                            }
                            appPrefe.setPreferences(this@LoginActivity, AppPreferencesLogin.KEY_PREFS_USERID,jSon.userid)
                            appPrefe.setPreferences(this@LoginActivity, AppPreferencesLogin.KEY_PREFS_SKEY,jSon.SKey!!)
                            appPrefe.setPreferences(this@LoginActivity, AppPreferencesLogin.KEY_PREFS_IDX,jSon.idx!!)
                            appPrefe.setPreferences(this@LoginActivity,AppPreferencesLogin.KEY_PREFS_LOGIN,true)
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

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Const.loge(TAG,"onFailure url :"+call.request().url)
                    Const.loge(TAG, "onFailure t :$t")
                }
            })
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

}