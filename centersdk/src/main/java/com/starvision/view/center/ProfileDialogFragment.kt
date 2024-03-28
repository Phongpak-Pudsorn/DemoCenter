package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.starvision.api.URL
import com.starvision.config.AESHelper
import com.starvision.config.CryptoHandler
import com.starvision.config.ParamsData
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.LoginActivity
import retrofit2.http.Body
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

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

        val timeStamp : String = SimpleDateFormat("HHmmssddMMyyyy").format(Date())
        val loadIdx = appPref.getPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_IDX,"").toString()
        val sKey = appPref.getPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_SKEY,"").toString()
        val idx = CryptoHandler().encrypt(appPref.getPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_IDX,"").toString(),sKey,"0000000000000000")
        val ts =  CryptoHandler().encrypt(timeStamp ,sKey,"0000000000000000")

//        val idx = AESHelper.encrypt(idxs,Const.AES_KEY)
//        val ts = AESHelper.encrypt(tss, Const.AES_KEY)
        val sign = "Starvision|$loadIdx|CheckProfile|$timeStamp"

//        {sign=Starvision|1001001000|CheckProfile|16165826032024, idx=wFN2NxGmXUW4qCLFoEmqwQ==, ts=SCcwm8Jo3OETfNyaK83sdA==}
//        {"idx":"B8Fz151GGq+MqrHyPWAoLmy+XrzrMlbzyzepKjwtkHw=","ts":"c2Ux1/9d5wBV4bY95M00O3WXNZA2jBENbch2XPmQz4s=","sign":"b8704a6af06a6d4740bd5ec393de1e4a"}

        val hashMap = HashMap<String?,String?>()
//        hashMap["idx"] = idx
//        hashMap["ts"] = ts
        hashMap["sign"] = sign
        Const.loge(TAG, "params : $hashMap")

        ParamsData(object : ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val jSon = Gson().fromJson(body,ProfileModels::class.java)
                    Const.loge(TAG," "+jSon.message)
                    Const.loge(TAG," "+jSon.code)
                    Const.loge(TAG," "+jSon.data!!.idx)
                    Const.loge(TAG," "+jSon.data.name)
                    Const.loge(TAG," "+jSon.data.avatar)
                    Const.loge(TAG," "+jSon.data.coin)

                    binding.tvName.text = jSon.data.name
                    binding.tvCoin.text = jSon.data.coin.toString()
                    binding.tvIdx.text = "Idx : "+jSon.data.idx
                    Glide.with(requireContext()).load(jSon.data.avatar).into(binding.imgProfile)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t : $t")
            }
        }).postLoadData(URL.BASE_URL_SDK,URL.URL_PROFILE,"",hashMap)

        dialog!!.show()
    }

    private fun bindingObject(){
        binding.cardviewImgPro.setOnClickListener {}
        binding.tvLogout.setOnClickListener {
            mClickListener.onLogout()
//            appPref.setPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_LOGIN,false)
            Const.KEY_PREFS_LOGIN = false
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }
    }

}