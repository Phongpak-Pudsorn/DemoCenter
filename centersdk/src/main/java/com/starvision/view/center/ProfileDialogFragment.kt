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
import com.starvision.config.*
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.LoginActivity
import com.starvision.view.login.WebViewPolicyDialogFragment
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
import kotlin.collections.HashMap

class ProfileDialogFragment : DialogFragment() {
    private val binding : PageFullProfileBinding by lazy { PageFullProfileBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onLogout()
        fun onCancel()
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
        dialog!!.show()
        dialog!!.setOnCancelListener {
            mClickListener.onCancel()
        }
    }

    private fun bindingObject(){
        binding.tvName.text = Login.Name
        binding.tvCoin.text = Login.Coin
        binding.tvIdx.text = "idx : "+Login.IDX
        Glide.with(requireContext()).load(Login.Avatar).into(binding.imgProfile)
        binding.tvLogout.setOnClickListener {
            mClickListener.onLogout()
            Login.isLogin = false
            Login.Name = ""
            Login.Avatar = ""
            Login.Coin = ""
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }
        binding.tvAccountManagement.setOnClickListener {
            DeleteAccountDialogFragment().show(childFragmentManager,"")
        }
        binding.tvPolicy.setOnClickListener {
            WebViewPolicyDialogFragment().show(childFragmentManager,"policy")
        }
        binding.imgTopup.setOnClickListener {

//            val params = ParamUtil.ParamsUid
            val params = kotlin.collections.HashMap<String?,String?>()
            val idx = CryptoHandler().encrypt(Login.IDX,Const.AES_KEY,"0000000000000000")
            val ts = CryptoHandler().encrypt(Const.timeStamp,Const.AES_KEY,"0000000000000000")
            val account_type = CryptoHandler().encrypt("s1",Const.AES_KEY,"0000000000000000")
            val channel_id = CryptoHandler().encrypt("StarVision",Const.AES_KEY,"0000000000000000")
            val server_sign = MD5.CMD5("TopupStar|${Login.IDX}|s1|StarVision|${Const.timeStamp}")

            params["idx"] = idx
            params["ts"] = ts
            params["account_type"] = account_type
            params["channel_id"] = channel_id
            params["server_sign"] = server_sign

            Const.loge(TAG,"params : $params")
            ParamsData(object : ParamsData.PostLoadListener{
                override fun onSuccess(body: String) {
                    Const.loge(TAG,"body : $body")

                }

                override fun onFailed(t: Throwable) {

                }
            }).getLoadData(URL.BASE_URL,URL.URL_TOPUP,"")
        }
    }

}