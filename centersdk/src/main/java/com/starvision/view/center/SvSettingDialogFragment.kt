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
import com.starvision.luckygamesdk.databinding.PageSettingBinding
import com.starvision.view.SvWebViewActivity
import com.starvision.view.login.SvLoginActivity
import com.starvision.view.login.SvWebViewPolicyDialogFragment
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys


class SvSettingDialogFragment : DialogFragment() {
    private val binding : PageSettingBinding by lazy { PageSettingBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName

    private lateinit var mClickSettingListener : ClickSettingListener
    interface ClickSettingListener {
        fun onLogout()
        fun onCancel()
        fun onDelete()
    }
    fun setClickListener(listener : ClickSettingListener) {
        mClickSettingListener = listener
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
            mClickSettingListener.onCancel()
        }
    }

    private fun bindingObject(){
        binding.tvLogout.setOnClickListener {
            mClickSettingListener.onLogout()
            SvLogin.isLogin = false
            SvLogin.Name = ""
            SvLogin.Avatar = ""
            SvLogin.Coin = ""
            val intent = Intent(requireContext(),SvLoginActivity::class.java)
            startActivity(intent)
        }
        binding.lnDelete.setOnClickListener {
            mClickSettingListener.onDelete()
        }
        binding.tvPolicy.setOnClickListener {
            SvWebViewPolicyDialogFragment().show(childFragmentManager,"policy")
        }
        binding.imgClose.setOnClickListener {
            dialog!!.dismiss()
        }
    }


}