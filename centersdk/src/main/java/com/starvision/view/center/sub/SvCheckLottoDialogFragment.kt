package com.starvision.view.center.sub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageCheckLottoBinding


class SvCheckLottoDialogFragment(private val number : String,private val token : String,
                                 private val checkTrue : String,private val date : String,
                                 private val boolean: Boolean) : DialogFragment() {
    private val binding : PageCheckLottoBinding by lazy { PageCheckLottoBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName

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
    }

    private fun bindingObject() {
        binding.cvClose.setOnClickListener { dialog!!.dismiss() }
        if(boolean) {
            binding.tvDate.text = date
            // T ถูก / F ไม่ถูก
            if (checkTrue == "T") {
                binding.tvNumber.text = number
                binding.tvToken.setTextColor(requireContext().getColor(R.color.green))
                binding.tvToken.text = token
            } else {
                binding.tvNumber.text = number
                binding.tvToken.setTextColor(requireContext().getColor(R.color.red))
                binding.tvToken.text = token
            }
        }else{
            binding.tvDate.text = date
            binding.tvNumber.text = getString(R.string.text_check_incomplete)
            binding.tvToken.visibility = View.GONE
        }
    }
}