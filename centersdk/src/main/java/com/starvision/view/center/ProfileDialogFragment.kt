package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.config.AESHelper
import com.starvision.config.CryptoHandler
import com.starvision.config.MD5
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.LoginActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*

class ProfileDialogFragment : DialogFragment() {
    private val binding : PageFullProfileBinding by lazy { PageFullProfileBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName
    private val appPref = AppPreferencesLogin

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onLogout()
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
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        bindingObject()

//        val IV = "0000000000000000"
        val timeStamp : String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val idx = "1001001000"
        val ts =  AESHelper.encrypt(timeStamp,Const.AES_KEY)
        val sign = MD5.CMD5("Starvision|$idx|CheckProfile|$ts")

        val hashMap = HashMap<String?,String?>()
        hashMap["idx"] = idx
        hashMap["ts"] = ts
        hashMap["sign"] = sign
        Const.loge(TAG, "params : $hashMap")
        val apiService = ApiClient().getBaseLink(URL.BASE_URL_SDK,"").create(Api::class.java)
        apiService.postRequest("/api/myprofile",hashMap).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Const.loge(TAG,"onResponse url :"+call.request().url)
                Const.loge(TAG,"onResponse :"+response.body()!!.string())
//                val jSon = Gson().fromJson(response.body()!!.string(),ProfileModels::class.java)
                try {
//                    Const.loge(TAG,"onResponse response :"+jSon.message)
                }catch (e : Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Const.loge(TAG,"onFailure url :"+call.request().url)
                Const.loge(TAG, "onFailure t :$t")
            }
        })

        dialog!!.show()
    }

    private fun bindingObject(){
        binding.cardviewImgPro.setOnClickListener {}
        binding.tvName
        binding.tvCoin
        binding.tvIdx
        binding.tvTopup
        binding.tvLogout.setOnClickListener {
            mClickListener.onLogout()
            appPref.setPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_LOGIN,false)
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }
    }

}