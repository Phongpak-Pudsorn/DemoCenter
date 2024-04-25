package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.starvision.api.SvURL
import com.starvision.config.SvCryptoHandler
import com.starvision.config.SvLogin
import com.starvision.config.SvMD5
import com.starvision.data.SvConst
import com.starvision.data.SvParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.SvWebViewActivity
import com.starvision.view.login.SvLoginActivity
import com.starvision.view.login.SvWebViewPolicyDialogFragment
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys


class SvProfileDialogFragment : DialogFragment() {
    private val binding : PageFullProfileBinding by lazy { PageFullProfileBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onLogout()
        fun onCancel()
        fun onDelete()
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
        binding.imgClose.setOnClickListener {
            mClickListener.onCancel()
            dialog!!.dismiss()
        }
    }

    private fun bindingObject(){
        //รอใช้งาน topup ได้ ค่อยเปิด
//        if (!SvConst.isReview){
//            binding.lnCoin.visibility = View.VISIBLE
//            binding.tvTopup.visibility = View.VISIBLE
//        }
        binding.tvName.text = SvLogin.Name
        binding.tvCoin.text = SvLogin.Coin
        binding.tvIdx.text = "idx : "+SvLogin.IDX
        Glide.with(requireContext()).load(SvLogin.Avatar).into(binding.imgProfile)
        binding.imgSetting.setOnClickListener {
            val settingFragment = SvSettingDialogFragment()
            settingFragment.setClickListener(object : SvSettingDialogFragment.ClickSettingListener{
                override fun onLogout() {
                    mClickListener.onLogout()
                }

                override fun onCancel() {
                    mClickListener.onCancel()
                }

                override fun onDelete() {
                    mClickListener.onDelete()
                }
            })
            settingFragment.show(childFragmentManager,"")
        }
        binding.tvTopup.setOnClickListener {
            val params = SvParamUtil.ParamsUid
            val account_type = SvCryptoHandler()
                .encrypt("s1",SvConst.AES_KEY,"0000000000000000")
            val channel_id = SvCryptoHandler()
                .encrypt("StarVision",SvConst.AES_KEY,"0000000000000000")
            val server_sign = SvMD5.CMD5("TopupStar|${SvLogin.IDX}|s1|StarVision|${SvConst.timeStamp}")

            params["ts"] = SvCryptoHandler()
                .encrypt(SvConst.timeStamp ,SvConst.AES_KEY,"0000000000000000")
            params["account_type"] = account_type
            params["channel_id"] = channel_id
            params["sign"] = server_sign

            val keys = Keys.hmacShaKeyFor(SvConst.AES_KEY.toByteArray())
            val jwtToken = Jwts.builder()
                .setClaims(params)
                .signWith(keys)
                .compact()

            SvConst.loge(TAG, "params : $params")
            SvConst.loge(TAG,"link : "+SvURL.BASE_URL+SvURL.URL_TOPUP+"?token="+jwtToken)
            val intent = Intent(requireContext(),SvWebViewActivity::class.java)
            intent.putExtra("title",getString(R.string.coupon))
            intent.putExtra("link",SvURL.BASE_URL+SvURL.URL_TOPUP+"?token="+jwtToken)
            startActivity(intent)
        }
//        if (SvConst.isSdkSDK){
//            binding.lnCoin.visibility = View.INVISIBLE
//        }
    }


}