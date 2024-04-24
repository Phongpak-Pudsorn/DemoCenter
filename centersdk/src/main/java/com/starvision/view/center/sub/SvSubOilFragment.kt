package com.starvision.view.center.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvParamsData
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageOilSubBinding
import com.starvision.view.center.sub.adapter.SvAdapterOilSub
import com.starvision.view.center.sub.models.SvSubOilModels
import com.starvision.view.center.sub.models.SvSubOilTodayModels


class SvSubOilFragment: DialogFragment() {
    val binding: PageOilSubBinding by lazy { PageOilSubBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName
    private val oilList = ArrayList<SvSubOilTodayModels>()

//    private lateinit var mClickListener : ClickListener
//    interface ClickListener {
//        fun onClickBack()
//    }
//    fun setClickListener(listener : ClickListener) {
//        mClickListener = listener
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
//        dialog!!.setCancelable(false)
        execeuteData()
        binding.tvNameApp.isSelected = true
        binding.tvDesApp.isSelected = true
        binding.cvApp.setOnClickListener {
//            Const.openApp(requireActivity(),getString(R.string.oil_package),"SplashActivity")
            SvConst.openAnotherApp(requireActivity(),getString(R.string.oil_package))
        }
        binding.imgBack.setOnClickListener {
//            try {
//                mClickListener.onClickBack()
//            }catch (e : Exception){
//                e.printStackTrace()
//            }
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
    private fun execeuteData(){
        binding.mProgressBar.visibility = View.VISIBLE
        oilList.clear()
        oilList.add(SvSubOilTodayModels("1","95E10",setName("95E10"),"0","",setOilIcon("95E10")))
        oilList.add(SvSubOilTodayModels("1","91E10",setName("91E10"),"0","",setOilIcon("91E10")))
        oilList.add(SvSubOilTodayModels("1","95E20",setName("95E20"),"0","",setOilIcon("95E20")))
        oilList.add(SvSubOilTodayModels("1","95E85",setName("95E85"),"0","",setOilIcon("95E85")))
        oilList.add(SvSubOilTodayModels("1","95",setName("95"),"0","",setOilIcon("95")))
        oilList.add(SvSubOilTodayModels("1","dieselpremium",setName("dieselpremium"),"0","",setOilIcon("dieselpremium")))
        oilList.add(SvSubOilTodayModels("1","diesel",setName("diesel"),"0","",setOilIcon("diesel")))
        oilList.add(SvSubOilTodayModels("1","ngv",setName("ngv"),"0","",setOilIcon("ngv")))
        oilList.add(SvSubOilTodayModels("1","footer","","","",""))
        SvParamsData(object :SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataOil = Gson().fromJson(body, SvSubOilModels::class.java)
                    if (dataOil.Status=="True") {
                        var listOil = dataOil.Datarow.data.price_today
                        for (i in listOil.indices){
                            for(j in listOil[i].data.indices){
                                listOil[i].data[j].id = listOil[i].oil
                                var oilPrice = try {
                                    listOil[i].data[j].today.toDouble()
                                }catch (e:java.lang.NumberFormatException){
                                    0.0
                                }
                                for (k in oilList.indices){
                                    var oilPrice1 = try {
                                        oilList[k].priceToday.toDouble()
                                    }catch (e:java.lang.NumberFormatException){
                                        0.0
                                    }
                                    if (oilList[k].priceType==listOil[i].data[j].type){
                                        if (oilPrice<=oilPrice1||oilPrice1<=0){
                                            var img1 = oilList[k].priceImg1
                                            var img2 = ""
                                            for (l in listOil.indices){
                                                if (listOil[l].oil==listOil[i].data[j].id){
                                                    img2 = listOil[l].image
                                                    break
                                                }
                                            }
                                            if (listOil[i].data[j].today.toDouble() < oilList[k].priceToday.toDouble()) {
                                                img1 = ""
                                            }
                                            oilList[k] = SvSubOilTodayModels("1",listOil[i].data[j].type,setName(listOil[i].data[j].type),listOil[i].data[j].today,img1+","+img2,setOilIcon(listOil[i].data[j].type))
                                        }
                                    }
                                }

                            }
                        }
                        binding.rvOil.apply {
                            adapter = SvAdapterOilSub(requireContext(), oilList)
                            layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        }
                        binding.mProgressBar.visibility = View.GONE
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t $t")
            }
        }).getLoadData(SvURL.BASE_URL,SvURL.oil_price,"")
        binding.imgBack.setOnClickListener {

        }

    }
    fun setName(oil: String): String {
        when (oil) {
            "95E10" -> {
                return getString(R.string.oil1)
            }
            "91E10" -> {
                return getString(R.string.oil2)
            }
            "95E20" -> {
                return getString(R.string.oil3)
            }
            "95E85" -> {
                return getString(R.string.oil4)
            }
            "95" -> {
                return getString(R.string.oil5)
            }
            "dieselpremium" -> {
                return getString(R.string.oil6)
            }
            "diesel" -> {
                return getString(R.string.oil7)
            }
            else -> {
                return getString(R.string.oil8)
            }
        }
    }
    fun setOilIcon(oil:String): String {
        when (oil) {
            "95E10" -> {
                return "oil1"
            }
            "91E10" -> {
                return "oil2"
            }
            "95E20" -> {
                return "oil3"
            }
            "95E85" -> {
                return "oil4"
            }
            "95" -> {
                return "oil5"
            }
            "dieselpremium" -> {
                return "oil6"
            }
            "diesel" -> {
                return "oil7"
            }
            else -> {
                return "oil8"
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        SvConst.clickAble = true
    }
}