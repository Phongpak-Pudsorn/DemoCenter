package com.starvision.view.stavisions

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvParamsData
import com.starvision.data.SvConst
import com.starvision.centersdk.databinding.PageStarvisionBinding
import com.starvision.models.SvCheckVersionModels
import com.starvision.view.center.models.SvCenterModels
import com.starvision.view.center.models.SvNewsModels
import com.starvision.view.stavisions.adapter.SvAdapterStarvision

class SvStarvisionFragment:Fragment() {
    val TAG = javaClass.simpleName
    val binding : PageStarvisionBinding by lazy { PageStarvisionBinding.inflate(layoutInflater) }
    var newsList = ArrayList<SvCenterModels.CenterData.PageData.NewsData.News>()
    var pinList = ArrayList<SvCenterModels.CenterData.PageData.NewsData.Pin>()
    var bannerList = ArrayList<SvCenterModels.CenterData.PageData.BannerData>()
    var appList = ArrayList<SvCenterModels.CenterData.PageData.IconData>()
    var apps = ArrayList<SvCenterModels.CenterData.PageData.IconData.IconDatarow>()
    var totalNew = ArrayList<SvCenterModels.CenterData.PageData.NewsData>()
    var stvAdapter : SvAdapterStarvision?=null
    var moreNews = ""
    var isLoading = false
    var loadCount = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val list = Gson().fromJson(body, SvCenterModels::class.java)
                if (list.code=="101") {
                    moreNews = list.data.PageCenter[0].LoadAPI
                    newsList.add(SvCenterModels.CenterData.PageData.NewsData.News(0,"banner","","","","","",""))
                    newsList.add(SvCenterModels.CenterData.PageData.NewsData.News(0,"header","","","","","",""))
                    for (i in list.data.PageCenter[0].BannerApp.indices){
                        if (list.data.PageCenter[0].BannerApp[i].bannerappLinkstoregoogle != SvConst.appPackage) {
//                            if(SvConst.checkStatusApp(list.data.PageCenter[0].BannerApp[i].bannerappId)) {
                                bannerList.add(list.data.PageCenter[0].BannerApp[i])
//                            }
                        }
                    }
                    for (i in list.data.PageCenter[0].IconApp.indices) {
                        appList.add(list.data.PageCenter[0].IconApp[i])
                    }
                        for (j in list.data.PageCenter[0].NewsApp.news.indices) {
                            newsList.add(list.data.PageCenter[0].NewsApp.news[j])
                        }
                        for (j in list.data.PageCenter[0].NewsApp.pin.indices) {
                            pinList.add(list.data.PageCenter[0].NewsApp.pin[j])
                        }
                    if (bannerList.size>=2) {
                        bannerList.add(0, bannerList[bannerList.size - 1])
                        bannerList.add(bannerList[1])
                    }
                    for (i in appList.indices) {
                        for (k in appList[i].iconappdatarow.indices) {
                            if (appList[i].iconappdatarow[k].iconappLinkstoregoogle == SvConst.appPackage) {
                                appList[i].iconappdatarow.remove(appList[i].iconappdatarow[k])
                                break
                            }
                        }
                    }
                    stvAdapter = SvAdapterStarvision(requireActivity(),newsList,pinList, bannerList,appList)
                    binding.rvMain.apply {
                        adapter = stvAdapter
                        layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                    }
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t $t")
            }
        }).getLoadData(SvURL.BASE_URL_SDK, SvURL.URL_CENTER,"")
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
        SvParamsData(object :SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val news = Gson().fromJson(body, SvNewsModels::class.java)
                if (news.code=="101"){
                    for (i in news.data.news.indices) {
                        newsList.add(SvCenterModels.CenterData.PageData.NewsData.News(news.data.news[i].newsId,news.data.news[i].newsappId,
                            news.data.news[i].newsappTitle,news.data.news[i].newsappImgNews,news.data.news[i].newsappUrlNews,
                            news.data.news[i].newsappLinkstoreapp,news.data.news[i].newsappLinkstoregoogle,news.data.news[i].newsappLinkkeyopenapp))
                    }
                    if (news.data.news.isEmpty()){
                        binding.footerLayout.visibility = View.GONE
                        isLoading = true
                    }else{
                        loadCount+=1
                        countDownTimer.start()
                    }
                }else{
                    binding.footerLayout.visibility = View.GONE
                    isLoading = true
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t $t")
            }
        }).getLoadData(SvURL.BASE_URL_SDK,moreNews+p,"")
    }

    fun checkIconList(list:ArrayList<SvCenterModels.CenterData.PageData.IconData>){
        for (i in list.indices) {
            for (j in list[i].iconappdatarow.indices){
                if (list[i].iconappdatarow[j].iconappLinkstoregoogle==SvConst.appPackage){
                    appList[i].iconappdatarow.removeAt(j)
                }
            }
        }
    }
}