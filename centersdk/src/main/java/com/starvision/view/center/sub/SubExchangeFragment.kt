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
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageExhangeSubBinding
import com.starvision.view.center.sub.adapter.AdapterExchangeSub
import com.starvision.view.center.sub.models.SubExchangeModel
import com.starvision.view.center.sub.models.SubGoldToDayModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SubExchangeFragment: Fragment() {
    val binding:PageExhangeSubBinding by lazy { PageExhangeSubBinding.inflate(layoutInflater) }
    val TAG = javaClass.simpleName
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
        binding.cvMore.setOnClickListener {
            Const.openAnotherApp(requireActivity(),getString(R.string.exchange_package))
        }
    }
    private fun executeData(){
        ParamsData(object :ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataExchange = Gson().fromJson(body,SubExchangeModel::class.java)
                    if (dataExchange.Status=="True"){
                        var  listExchange = dataExchange.Datarow
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

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t $t")
            }
        }).getLoadData(URL.BASE_URL,URL.exchange,"")
    }
}