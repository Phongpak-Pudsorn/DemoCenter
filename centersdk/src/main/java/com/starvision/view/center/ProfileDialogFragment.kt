package com.starvision.view.center

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.starvision.api.URL
import com.starvision.config.CryptoHandler
import com.starvision.config.Login
import com.starvision.config.MD5
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.WebViewActivity
import com.starvision.view.login.LoginActivity
import com.starvision.view.login.WebViewPolicyDialogFragment
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection


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
        binding.lnTopup.setOnClickListener {
            val params = ParamUtil.ParamsUid
            val account_type = CryptoHandler().encrypt("s1",Const.AES_KEY,"0000000000000000")
            val channel_id = CryptoHandler().encrypt("StarVision",Const.AES_KEY,"0000000000000000")
            val server_sign = MD5.CMD5("TopupStar|${Login.IDX}|s1|StarVision|${Const.timeStamp}")

            params["ts"] = CryptoHandler().encrypt(Const.timeStamp ,Const.AES_KEY,"0000000000000000")
            params["account_type"] = account_type
            params["channel_id"] = channel_id
            params["server_sign"] = server_sign

            val keys = Keys.hmacShaKeyFor(Const.AES_KEY.toByteArray())
            val jwtToken = Jwts.builder()
                .setClaims(params)
                .signWith(keys)
                .compact()

            Const.loge(TAG, "params : $params")
            Const.loge(TAG,"link : "+URL.BASE_URL+URL.URL_TOPUP+"?token="+jwtToken)
            val intent = Intent(requireContext(),WebViewActivity::class.java)
            intent.putExtra("title",getString(R.string.coupon))
            intent.putExtra("link",URL.BASE_URL+URL.URL_TOPUP+"?token="+jwtToken)
            startActivity(intent)
        }
    }


}