package com.starvision.view.center.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageMoreappSubBinding
import com.starvision.view.center.models.SvCenterModels
import com.starvision.view.center.sub.adapter.SvAdapterMoreApp

class SvSubMoreApp(
    private val context: FragmentActivity,
    private val appList: ArrayList<SvCenterModels.CenterData.PageData.IconData>
) : DialogFragment() {
    private val binding : PageMoreappSubBinding by lazy { PageMoreappSubBinding.inflate(layoutInflater) }

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
        binding.imgBack.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.recycleviewMoreapp.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recycleviewMoreapp.adapter = SvAdapterMoreApp(context,appList)
        binding.mProgressBar.visibility = View.GONE

    }
}