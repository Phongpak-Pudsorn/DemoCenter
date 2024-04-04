package com.starvision.view.center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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
//        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.show()


    }
}