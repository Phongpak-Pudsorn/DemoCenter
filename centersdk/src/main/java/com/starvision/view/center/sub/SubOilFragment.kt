package com.starvision.view.center.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageOilSubBinding
import com.starvision.view.center.sub.adapter.AdapterOilSub
import com.starvision.view.center.sub.models.SubExchangeModel
import com.starvision.view.center.sub.models.SubOilModel
import com.starvision.view.center.sub.models.SubOilTodayModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubOilFragment: Fragment() {
    val binding: PageOilSubBinding by lazy { PageOilSubBinding.inflate(layoutInflater) }
    private val oilList = ArrayList<SubOilTodayModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        execeuteData()

    }
    private fun execeuteData(){
        binding.mProgressBar.visibility = View.VISIBLE
        oilList.clear()
        oilList.add(SubOilTodayModel("1","95E10",setName("95E10"),"0","","","","",setOilIcon("95E10")))
        oilList.add(SubOilTodayModel("1","91E10",setName("91E10"),"0","","","","",setOilIcon("91E10")))
        oilList.add(SubOilTodayModel("1","95E20",setName("95E20"),"0","","","","",setOilIcon("95E20")))
        oilList.add(SubOilTodayModel("1","95E85",setName("95E85"),"0","","","","",setOilIcon("95E85")))
        oilList.add(SubOilTodayModel("1","95",setName("95"),"0","","","","",setOilIcon("95")))
        oilList.add(SubOilTodayModel("1","dieselpremium",setName("dieselpremium"),"0","","","","",setOilIcon("dieselpremium")))
        oilList.add(SubOilTodayModel("1","diesel",setName("diesel"),"0","","","","",setOilIcon("diesel")))
        oilList.add(SubOilTodayModel("1","ngv",setName("ngv"),"0","","","","",setOilIcon("ngv")))
        val services = ApiClient().getBaseLink(URL.BASE_URL,":443").create(Api::class.java)
        services.getOil().enqueue(object :Callback<SubOilModel>{
            override fun onResponse(call: Call<SubOilModel>, response: Response<SubOilModel>) {
                try {
                    val gsonOil = Gson().toJson(response.body()!!, SubOilModel::class.java)
                    val dataOil = Gson().fromJson(gsonOil, SubOilModel::class.java)
                    var listOil = dataOil.Datarow.data.price_today
                    if (dataOil.Status=="True") {
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
                                            val img1 = oilList[k].priceImg1
                                            var img2 = ""
                                            for (l in listOil.indices){
                                                //กลับมาแก้ listOil[i].data[j].name ด้วย
                                                if (listOil[l].oil==listOil[i].data[j].id){
                                                    img2 = listOil[l].image
                                                    break
                                                }
                                            }
                                            oilList[k] = SubOilTodayModel("1",listOil[i].data[j].type,setName(listOil[i].data[j].type),listOil[i].data[j].today,img1,img2,"","",setOilIcon(listOil[i].data[j].type))
                                        }
                                    }
                                }

                            }
                        }
                        binding.rvOil.apply {
                            adapter = AdapterOilSub(requireContext(), oilList)
                            layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        }
                        binding.mProgressBar.visibility = View.GONE
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<SubOilModel>, t: Throwable) {
            }
        })

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
}