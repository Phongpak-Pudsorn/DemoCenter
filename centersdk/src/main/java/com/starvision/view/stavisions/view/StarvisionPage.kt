package com.starvision.view.stavisions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.StarvisionPageBinding
import com.starvision.view.stavisions.adapter.AdapterStarvision

class StarvisionPage:Fragment() {
    val binding : StarvisionPageBinding by lazy { StarvisionPageBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.apply {
            adapter = AdapterStarvision(requireActivity())
            layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }
}