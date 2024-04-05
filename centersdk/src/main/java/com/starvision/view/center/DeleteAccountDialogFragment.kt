package com.starvision.view.center

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.starvision.config.ParamsData
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageDeleteAccountBinding

class DeleteAccountDialogFragment : DialogFragment() {
    private val binding : PageDeleteAccountBinding by lazy { PageDeleteAccountBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.setCancelable(false)
        dialog!!.show()
        binding.imgClose.setOnClickListener {
            dismiss()
        }
        binding.tvDelete.setOnClickListener {
            if(!binding.checkBox.isChecked){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_delete_uncheck),Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else{

            }
        }

    }
}