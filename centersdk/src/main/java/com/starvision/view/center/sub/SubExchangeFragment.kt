package com.starvision.view.center.sub

import android.os.Bundle
import android.util.Log
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
import com.starvision.luckygamesdk.databinding.PageExhangeSubBinding
import com.starvision.view.center.sub.adapter.AdapterExchangeSub
import com.starvision.view.center.sub.models.SubExchangeModel
import com.starvision.view.center.sub.models.SubGoldToDayModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SubExchangeFragment: Fragment() {
    val binding:PageExhangeSubBinding by lazy { PageExhangeSubBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executeData()
    }
    private fun executeData(){
        val services = ApiClient().getBaseLink(URL.BASE_URL,":443").create(Api::class.java)
        services.getExchange().enqueue(object : retrofit2.Callback<SubExchangeModel>{
            override fun onResponse(
                call: Call<SubExchangeModel>,
                response: Response<SubExchangeModel>,
            ) {
                try {
                    val gsonExchange = Gson().toJson(response.body()!!, SubExchangeModel::class.java)
                    val dataExchange = Gson().fromJson(gsonExchange, SubExchangeModel::class.java)
                    var listExchange = dataExchange.Datarow
                    if (dataExchange.Status=="True"){
                        binding.rvExchange.apply {
                            adapter = AdapterExchangeSub(requireContext(),listExchange)
                            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                        }
                        binding.mProgressBar.visibility = View.GONE
                        binding.tableCurrency.visibility = View.VISIBLE
                        binding.tabHeader.visibility = View.VISIBLE
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<SubExchangeModel>, t: Throwable) {
            }
        })
    }
}