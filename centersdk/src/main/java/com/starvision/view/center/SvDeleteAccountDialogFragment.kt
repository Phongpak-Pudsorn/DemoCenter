package com.starvision.view.center

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageDeleteAccountBinding
import com.starvision.view.center.sub.SvAnotherDialogFragment

class SvDeleteAccountDialogFragment : DialogFragment() {
    private val binding : PageDeleteAccountBinding by lazy { PageDeleteAccountBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private val TAG = javaClass.simpleName

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onClickBack()
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
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.show()
        binding.tvText.text = getString(R.string.text_alert_delete_account)+"\n"+"\n"
//        +
//                getString(R.string.text_alert_delete_account1)+"\n"+
//        getString(R.string.text_alert_delete_account2)+"\n"+
//        getString(R.string.text_alert_delete_account3)+"\n"
        binding.imgBack.setOnClickListener {
            dialog!!.dismiss()
        }
        binding.tvDelete.setOnClickListener {
            binding.tvDelete.isEnabled = false
            handler.postDelayed({binding.tvDelete.isEnabled = true},1000)
            if(!binding.checkBox.isChecked){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_delete_uncheck),Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else{
                val dialog = SvAnotherDialogFragment()
                dialog.setClickListener(object : SvAnotherDialogFragment.ClickListener{
                    override fun onClickBack() {
                        mClickListener.onClickBack()
                    }
                })
                dialog.show(childFragmentManager,"")
            }
        }

    }
}