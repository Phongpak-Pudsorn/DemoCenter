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
import com.starvision.luckygamesdk.databinding.PageForgotBinding

class ForgotPage(private val bm: Bitmap) : Fragment() {
    private val binding : PageForgotBinding by lazy { PageForgotBinding.inflate(layoutInflater) }
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
        binding.cvSent.setOnClickListener {
            binding.cvSent.isEnabled = false
            handler.postDelayed({ binding.cvSent.isEnabled = true },1000)
            if(binding.editEmail.length() <= 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_email_min), Toast.LENGTH_SHORT).show()
            }else if(binding.editUsername.length() <= 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_user_min), Toast.LENGTH_SHORT).show()
            }else{
                // set เหลือส่งเข้าระบบ
                Toast.makeText(requireContext(),getString(R.string.text_alert_recovery), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnBack.setOnClickListener {
            mCloseListener.onClose()
        }

    }
}