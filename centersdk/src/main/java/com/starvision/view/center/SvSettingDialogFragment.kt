package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private val handler = Handler(Looper.getMainLooper())

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
        binding.lnLogout.setOnClickListener {
            binding.lnLogout.isEnabled = false
            handler.postDelayed({binding.lnLogout.isEnabled = true},1000)
            val logoutDialog = SvLogoutConfirmDialogFragment()
            logoutDialog.setClickListener(object : SvLogoutConfirmDialogFragment.ConfirmListener{
                override fun onLogout() {
                    mClickSettingListener.onLogout()
                    SvLogin.isLogin = false
                    SvLogin.Name = ""
                    SvLogin.Avatar = ""
                    SvLogin.Coin = ""
                    val intent = Intent(requireContext(),SvLoginActivity::class.java)
                    startActivity(intent)
                }

                override fun onCancel() {

                }
            })
            logoutDialog.show(childFragmentManager,"")
        }
        binding.lnDelete.setOnClickListener {
            binding.lnDelete.isEnabled = false
            handler.postDelayed({binding.lnDelete.isEnabled = true},1000)
            mClickSettingListener.onDelete()
        }
        binding.lnPolicy.setOnClickListener {
            binding.lnPolicy.isEnabled = false
            handler.postDelayed({binding.lnPolicy.isEnabled = true},1000)
            SvWebViewPolicyDialogFragment().show(childFragmentManager,"policy")
        }
        binding.imgClose.setOnClickListener {
            dialog!!.dismiss()
        }
    }


}