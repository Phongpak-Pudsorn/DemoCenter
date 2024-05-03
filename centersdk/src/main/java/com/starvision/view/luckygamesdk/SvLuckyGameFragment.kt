package com.starvision.view.luckygamesdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.databinding.LuckyGamePageBinding
import com.starvision.view.luckygamesdk.adapter.SvAdapterGamePage
import com.starvision.view.luckygamesdk.adapter.SvAdapterLucky

class SvLuckyGameFragment : Fragment(){
    private val binding : LuckyGamePageBinding by lazy { LuckyGamePageBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName
    private var gameAdapter : SvAdapterGamePage? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        gameAdapter = SvAdapterGamePage(requireActivity())
        binding.recycleView.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        binding.recycleView.adapter = SvAdapterLucky(requireActivity())

    }

}