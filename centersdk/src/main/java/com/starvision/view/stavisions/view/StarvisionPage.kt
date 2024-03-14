package com.starvision.view.stavisions.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.StarvisionPageBinding
import com.starvision.view.center.info.TabInfo
import com.starvision.view.stavisions.adapter.AdapterDots
import com.starvision.view.stavisions.adapter.AdapterImageSlide
import com.starvision.view.stavisions.adapter.AdapterStarvision
import com.starvision.view.stavisions.info.BannerInfo
import com.starvision.view.stavisions.info.NewsInfo

class StarvisionPage:Fragment() {
    val binding : StarvisionPageBinding by lazy { StarvisionPageBinding.inflate(layoutInflater) }
    var newsList = ArrayList<NewsInfo>()
    var bannerList = ArrayList<BannerInfo>()
    var dotsAdapter : AdapterDots?=null
    var imageAdapter :AdapterImageSlide?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsList = setNews()
        bannerList =setBanner()
        dotsAdapter = AdapterDots(requireContext(),bannerList)
        imageAdapter = AdapterImageSlide(requireContext(),bannerList)
        Log.e("newsList",newsList.size.toString())
        binding.rvMain.apply {
            adapter = AdapterStarvision(requireContext(),newsList, dotsAdapter!!, imageAdapter!!)
            layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }
    private fun setNews(): ArrayList<NewsInfo> {
        newsList.clear()
        newsList.add(NewsInfo("banner","",""))
        newsList.add(NewsInfo("header","",""))
        newsList.add(NewsInfo("app","",""))
        newsList.add(NewsInfo("1","","News 1"))
        newsList.add(NewsInfo("2","","News 2"))
        newsList.add(NewsInfo("3","","News 3"))
        newsList.add(NewsInfo("4","","News 4"))
        newsList.add(NewsInfo("5","","News 5"))
        newsList.add(NewsInfo("6","","News 6"))
        newsList.add(NewsInfo("7","","News 7"))
        newsList.add(NewsInfo("8","","News 8"))
        newsList.add(NewsInfo("9","","News 9"))
        newsList.add(NewsInfo("10","","News 10"))
        return newsList
    }
    private fun setBanner(): ArrayList<BannerInfo> {
        bannerList.clear()
        bannerList.add(BannerInfo("1","","News 1"))
        bannerList.add(BannerInfo("2","","News 2"))
        bannerList.add(BannerInfo("3","","News 3"))
        bannerList.add(BannerInfo("4","","News 4"))
        bannerList.add(BannerInfo("5","","News 5"))
        bannerList.add(BannerInfo("6","","News 6"))
        bannerList.add(BannerInfo("7","","News 7"))
        bannerList.add(BannerInfo("8","","News 8"))
        bannerList.add(BannerInfo("9","","News 9"))
        bannerList.add(BannerInfo("10","","News 10"))
        return bannerList
    }
}