package com.starvision.view.luckygamesdk.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.starvision.view.luckygamesdk.adapter.AdapterGamePage
import com.starvision.luckygamesdk.databinding.LuckyGamePageBinding

class LuckyGamePage : Fragment(){
    private val binding : LuckyGamePageBinding by lazy { LuckyGamePageBinding.inflate(layoutInflater) }
    private var gameAdapter : AdapterGamePage? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameAdapter = AdapterGamePage(requireActivity())
        binding.recycleView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recycleView.adapter = gameAdapter

    }

}