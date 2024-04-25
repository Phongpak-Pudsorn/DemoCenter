package com.starvision.view.center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageLogoutConfirmBinding

class SvLogoutConfirmDialogFragment:DialogFragment() {
    val binding: PageLogoutConfirmBinding by lazy { PageLogoutConfirmBinding.inflate(layoutInflater) }
    private lateinit var mConfirmListener : ConfirmListener
    interface ConfirmListener {
        fun onLogout()
        fun onCancel()
    }
    fun setClickListener(listener : ConfirmListener) {
        mConfirmListener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.show()
        dialog!!.setOnCancelListener {
            mConfirmListener.onCancel()
            dialog!!.dismiss()
        }
        binding.btnYes.setOnClickListener {
            mConfirmListener.onLogout()
        }
        binding.btnNo.setOnClickListener {
            mConfirmListener.onCancel()
            dialog!!.dismiss()
        }
    }
}