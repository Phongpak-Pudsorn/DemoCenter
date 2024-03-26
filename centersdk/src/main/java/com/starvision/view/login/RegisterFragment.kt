package com.starvision.view.login

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.config.MD5
import com.starvision.config.ParamsData
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageRegisterBinding
import com.starvision.view.center.MainActivity
import com.starvision.view.login.models.LoginModels
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.util.*
import kotlin.collections.HashMap

class RegisterFragment(private val bm: Bitmap) : Fragment() {
    private val binding : PageRegisterBinding by lazy { PageRegisterBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private val TAG = javaClass.simpleName
    private val appPrefe = AppPreferencesLogin

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onClose()
        fun onSuccess()
    }
    fun setClickListener(listener : ClickListener) {
        mClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgLogo.setImageBitmap(bm)
        binding.cvRegister.setOnClickListener {
            binding.cvRegister.isEnabled = false
            handler.postDelayed({ binding.cvRegister.isEnabled = true },1000)
            if(binding.editUsername.length() <= 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_user_min), Toast.LENGTH_SHORT).show()
            }else if(binding.editPassword.length() <= 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT).show()
            }else if(binding.editConfirmPassword.length() <= 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_password_not_same), Toast.LENGTH_SHORT).show()
            }else if(binding.editEmail.length() <= 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_email_min), Toast.LENGTH_SHORT).show()
            }else if(!binding.checkboxAcceptPolicy.isChecked){
                Toast.makeText(requireContext(),getString(R.string.text_alert_policy_uncheck), Toast.LENGTH_SHORT).show()
            }else{
                val acc_name = binding.editUsername.text.toString().lowercase(Locale.getDefault())
                val password = MD5.CMD5(binding.editPassword.text.toString())
                val confirmpassword = MD5.CMD5(binding.editConfirmPassword.text.toString())
                val sign = MD5.CMD5("Starvision|$acc_name|Register|$password")
                val account_type = "s1"
                val ChannelId = "StarVision"
                val platform = "Android "+ Build.VERSION.SDK_INT
                val imei = Const.getUUID(requireContext())
                val model = LoginActivity().getDeviceName()
                val phonenumber = ""
                val hashMap = HashMap<String?,String?>()

                hashMap["acc_name"] = acc_name
                hashMap["password"] = password
                hashMap["confirmpassword"] = confirmpassword
                hashMap["sign"] = sign
                hashMap["account_type"] = account_type
                hashMap["ChannelId"] = ChannelId
                hashMap["platform"] = platform
                hashMap["imei"] = imei
                hashMap["model"] = model
                hashMap["phonenumber"] = phonenumber

                Const.loge(TAG,"params : $hashMap")

                ParamsData(object : ParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        try {
                            val jSon = Gson().fromJson(body, LoginModels::class.java)
                            if(jSon.message == "success"){
                                appPrefe.setPreferences(requireContext(), AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,true)
                                appPrefe.setPreferences(requireContext(), AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,binding.editUsername.text.toString())
                                appPrefe.setPreferences(requireContext(), AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,binding.editPassword.text.toString())

                                appPrefe.setPreferences(requireContext(), AppPreferencesLogin.KEY_PREFS_USERID,jSon.userid)
                                appPrefe.setPreferences(requireContext(), AppPreferencesLogin.KEY_PREFS_SKEY,jSon.SKey!!)
                                appPrefe.setPreferences(requireContext(), AppPreferencesLogin.KEY_PREFS_IDX,jSon.idx!!)
                                Toast.makeText(requireContext(),jSon.message,Toast.LENGTH_SHORT).show()
                                mClickListener.onSuccess()
                            }else{
                                Toast.makeText(requireContext(),jSon.message,Toast.LENGTH_SHORT).show()
                            }
                        }catch (e : Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onFailed(t: Throwable) {
                        Const.loge(TAG,"t : $t")
                    }
                }).postLoadData(URL.BASE_URL_SDK,URL.URL_LOGIN,"",hashMap)
            }
        }

        binding.tvPolicyRegister.setOnClickListener {
            binding.tvPolicyRegister.isEnabled = false
            handler.postDelayed({ binding.tvPolicyRegister.isEnabled = true },1000)
            WebViewPolicyDialogFragment().show(childFragmentManager,"policy")
        }

        binding.btnBack.setOnClickListener {
            mClickListener.onClose()
        }
    }

}