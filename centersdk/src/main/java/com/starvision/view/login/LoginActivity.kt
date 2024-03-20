package com.starvision.view.login

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
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.config.AESHelper
import com.starvision.config.MD5
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageLoginBinding
import com.starvision.view.login.models.LoginModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val binding : PageLoginBinding by lazy { PageLoginBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private var callback : OnBackPressedCallback? = null
    private var appPrefe = AppPreferencesLogin
    private val TAG = javaClass.simpleName
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bm = getBitmapFromAsset("logo_starvision.png")
        binding.imgLogo.setImageBitmap(bm)
        binding.tvRegister.setOnClickListener {
            val registerFragment = RegisterFragment(bm)
            registerFragment.setCloseListener(object : RegisterFragment.CloseListener {
                override fun onClose() {
                    toggle()
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
            handler.postDelayed({ binding.cvLogin.isEnabled = true },1000)
            if(binding.editUsername.length() < 6){
                Toast.makeText(this,getString(R.string.text_alert_user_min),Toast.LENGTH_SHORT).show()
            }else if(binding.editPassword.length() < 6){
                Toast.makeText(this,getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT).show()
            }else{
                if(binding.checkboxRememberPass.isChecked){
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true)
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,binding.editUsername.text.toString())
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,binding.editPassword.text.toString())
                }else{
                    appPrefe.setPreferences(this,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,false)
                }

                val password = AESHelper.encrypt(binding.editUsername.text.toString(),Const.AES_KEY)
                val imei = Const.getUUID(this)
                val platform = "Android "+Build.VERSION.SDK_INT
                val model = getDeviceName()
                val ChannelId = "StarVision"
                val phonenumber = ""
                val acc_name = binding.editUsername.text
                val account_type = "s1"
                val sign = MD5.CMD5("password|$password|" +
                        "imei|$imei|" +
                        "platform|$platform|" +
                        "model|$model|" +
                        "ChannelId|$ChannelId|" +
                        "phonenumber|$phonenumber" +
                        "|acc_name|$acc_name|" +
                        "account_type|$account_type")

                Const.loge(TAG,"sign : $sign")
                Const.loge(TAG,"password : $password")
                Const.loge(TAG,"imei : $imei")
                Const.loge(TAG,"platform : $platform")
                Const.loge(TAG,"model : $model")
                Const.loge(TAG,"acc_name : $acc_name")


//                val services = ApiClient().getBaseLink(URL.BASE_URL_SDK,"").create(Api::class.java)
//                services.getLogin(sign)!!.enqueue(object : Callback<LoginModels?> {
//                    override fun onResponse(call: Call<LoginModels?>, response: Response<LoginModels?>) {
//                        Const.loge(TAG," onResponse : "+call.request().url)
//                        Const.loge(TAG," onResponse response: "+response.body()!!)
//                    }
//
//                    override fun onFailure(call: Call<LoginModels?>, t: Throwable) {
//                        Const.loge(TAG," onFailure : "+call.request().url)
//                        Const.loge(TAG," onFailure : $t")
//                    }
//                })

                //รอเซ็ต auto login จาก regis
//                val intent = Intent(this,MainPage::class.java)
//                startActivity(intent)
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
    private fun getDeviceName(): String {
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {
            // in the below line, we are checking if permission is granted.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if permissions are granted we are displaying below toast message.
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
            } else {
                // in the below line, we are displaying toast message if permissions are not granted.
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}