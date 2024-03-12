package com.starvision.view.login.view

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageRegisterBinding

class RegisterPage(private val bm: Bitmap) : Fragment() {
    private val binding : PageRegisterBinding by lazy { PageRegisterBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var mCloseListener : CloseListener
    interface CloseListener {
        fun onClose()
    }
    fun setCloseListener(listener : CloseListener) {
        mCloseListener = listener
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
                // set เหลือส่งเข้าระบบ
                Toast.makeText(requireContext(),getString(R.string.text_alert_recovery), Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvPolicyRegister.setOnClickListener {
            binding.tvPolicyRegister.isEnabled = false
            handler.postDelayed({ binding.tvPolicyRegister.isEnabled = true },1000)
            WebViewPolicyPage().show(childFragmentManager,"policy")
        }

        binding.btnBack.setOnClickListener {
            mCloseListener.onClose()
        }
    }

}