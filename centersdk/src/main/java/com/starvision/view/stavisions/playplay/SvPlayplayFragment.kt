package com.starvision.view.stavisions.playplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.databinding.PageStarvisionBinding
import com.starvision.view.stavisions.playplay.adapter.SvAdapterPlayplay

class SvPlayplayFragment:Fragment() {
    val binding:PageStarvisionBinding by lazy { PageStarvisionBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.apply {
            adapter = SvAdapterPlayplay(requireActivity())
            layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }
}