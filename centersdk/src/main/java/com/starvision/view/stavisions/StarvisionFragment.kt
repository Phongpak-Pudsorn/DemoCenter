package com.starvision.view.stavisions

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.starvision.api.URL
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.luckygamesdk.databinding.PageStarvisionBinding
import com.starvision.view.center.models.CenterModels
import com.starvision.view.center.models.NewsModels
import com.starvision.view.stavisions.adapter.AdapterStarvision

class StarvisionFragment:Fragment() {
    val TAG = javaClass.simpleName
    val binding : PageStarvisionBinding by lazy { PageStarvisionBinding.inflate(layoutInflater) }
    var newsList = ArrayList<CenterModels.CenterData.PageData.NewsData>()
    var bannerList = ArrayList<CenterModels.CenterData.PageData.BannerData>()
    var appList = ArrayList<CenterModels.CenterData.PageData.IconData>()
    var stvAdapter : AdapterStarvision?=null
    var moreNews = ""
    var isLoading = false
    var loadCount = 1
    val getPackage =  ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        binding.rvMain.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == newsList.size - 1){
                        loadMore()
                        recyclerView.scrollToPosition(newsList.size-1)
                    }

                }
            }
        })
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            binding.rvMain.visibility = View.INVISIBLE
            loadCount = 1
            isLoading = false
            loadData()
            val countDownTimer = object : CountDownTimer(2000, 1000) {
                override fun onTick(l: Long) {}
                override fun onFinish() {
                    binding.rvMain.visibility = View.VISIBLE
                    binding.swipeRefresh.isRefreshing = false
                }
            }
            countDownTimer.start()
        }
    }
    private fun loadData(){
        newsList.clear()
        bannerList.clear()
        appList.clear()
        ParamsData(object : ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val list = Gson().fromJson(body, CenterModels::class.java)
                if (list.code=="101") {
                    moreNews = list.data.PageCenter[0].LoadAPI
                    newsList.add(CenterModels.CenterData.PageData.NewsData(0,"banner","","","","","",""))
                    newsList.add(CenterModels.CenterData.PageData.NewsData(0,"header","","","","","",""))
                    for (i in list.data.PageCenter[0].BannerApp.indices){
                        if (list.data.PageCenter[0].BannerApp[i].bannerappLinkstoregoogle!=getPackage) {
                            bannerList.add(list.data.PageCenter[0].BannerApp[i])
                        }
                    }
                    for (i in list.data.PageCenter[0].IconApp.indices){
                        if (list.data.PageCenter[0].IconApp[i].iconappLinkstoregoogle!=getPackage) {
                            appList.add(list.data.PageCenter[0].IconApp[i])
                        }
                    }
                    for (i in list.data.PageCenter[0].NewsApp.indices){
                        newsList.add(list.data.PageCenter[0].NewsApp[i])
                    }

                    if (bannerList.size>=2) {
                        bannerList.add(0, bannerList[bannerList.size - 1])
                        bannerList.add(bannerList[1])
                    }
                    stvAdapter = AdapterStarvision(requireContext(),newsList, bannerList,appList)
                    binding.rvMain.apply {
                        adapter = stvAdapter
                        layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                    }
                }
            }

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t $t")
            }
        }).getLoadData(URL.BASE_URL_SDK, URL.URL_CENTER,"")
    }
    private fun loadMore(){
        val p = "?p=$loadCount"
        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                stvAdapter!!.notifyDataSetChanged()
                isLoading = false
                binding.footerLayout.visibility = View.GONE
            }
        }
        if (!isLoading) {
            binding.footerLayout.visibility = View.VISIBLE
            isLoading = true
        }
        ParamsData(object :ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val news = Gson().fromJson(body, NewsModels::class.java)
                if (news.code=="101"){
                    for (i in news.data.indices) {
                        newsList.add(CenterModels.CenterData.PageData.NewsData(news.data[i].newsId,news.data[i].newsappId,news.data[i].newsappTitle,news.data[i].newsappImgNews,news.data[i].newsappUrlNews,news.data[i].newsappLinkstoreapp,news.data[i].newsappLinkstoregoogle,news.data[i].newsappLinkkeyopenapp))
                    }
                    loadCount+=1
                    countDownTimer.start()
                }else{
                    binding.footerLayout.visibility = View.GONE
                    isLoading = true
                }
            }

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t $t")
            }
        }).getLoadData(URL.BASE_URL_SDK,moreNews+p,"")
    }
}