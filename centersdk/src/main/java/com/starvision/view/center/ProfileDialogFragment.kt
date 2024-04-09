package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.starvision.api.URL
import com.starvision.config.*
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.login.LoginActivity
import com.starvision.view.login.WebViewPolicyDialogFragment
import io.jsonwebtoken.Jwts
import javax.crypto.spec.SecretKeySpec

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

            val params = ParamUtil.ParamsUid
            val account_type = CryptoHandler().encrypt("s1",Const.AES_KEY,"0000000000000000")
            val channel_id = CryptoHandler().encrypt("StarVision",Const.AES_KEY,"0000000000000000")
            val server_sign = MD5.CMD5("TopupStar|${Login.IDX}|s1|StarVision|${Const.timeStamp}")

            params["account_type"] = account_type
            params["channel_id"] = channel_id
            params["server_sign"] = server_sign

//            val keyBytes = Encoder.BASE64URL.decode("{signing_secret}")
//            val key: Key = Keys.hmacShaKeyFor(keyBytes)
//            val skeySpec = SecretKeySpec(Const.AES_KEY.substring(0, 32).toByteArray(), "AES")

//            val jwt: String = Jwts.builder()
//                .setHeaderParam("dd-ver", "DD-JWT-V1")
//                .setHeaderParam("typ", "JWT")
//                .setClaims(params)
//                .signWith(key)
//                .compact();

            Const.loge(TAG,"params : $params")
            ParamsData(object : ParamsData.PostLoadListener{
                override fun onSuccess(body: String) {
                    Const.loge(TAG,"body : $body")

                }

                override fun onFailed(t: Throwable) {

                }
            }).getLoadData(URL.BASE_URL,URL.URL_TOPUP+"?token="+"jwt","")
        }
    }

//    {
//        "server_sign":"bbbfd88d2e68b3943b88010661f0e046",
//        "account_type":"2u19pYWvQlptCH8p5ygWZA==",
//        "imei":"f27c8b7a-77ac-342c-8b39-d97e90dd5ce1",
//        "model":"SM-A135F",
//        "idx":"DEvdofF6NfxHATiKwUm7lQ==",
//        "channel_id":"Ye8wCaYOb30937bfkAI42g==",
//        "platform":"Android 33",
//        "ts":"o+p0/EktU4HllvUhL61wEQ=="
//    }

}