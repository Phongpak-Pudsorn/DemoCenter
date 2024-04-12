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
import com.starvision.luckygamesdk.databinding.PageExhangeSubBinding
import com.starvision.view.center.sub.adapter.SvAdapterExchangeSub
import com.starvision.view.center.sub.models.SvSubExchangeModels

class SvSubExchangeFragment: DialogFragment() {
    val binding:PageExhangeSubBinding by lazy { PageExhangeSubBinding.inflate(layoutInflater) }
    val TAG = javaClass.simpleName
    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onClickBack()
    }
    fun setClickListener(listener : ClickListener) {
        mClickListener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
//        dialog!!.setCancelable(false)
        executeData()
        binding.tvNameApp.isSelected = true
        binding.tvDesApp.isSelected = true
        binding.cvMore.setOnClickListener {
                SvConst.openAnotherApp(requireActivity(), getString(R.string.exchange_package))
        }
        binding.imgBack.setOnClickListener {
            try {
                mClickListener.onClickBack()
            }catch (e : Exception){
                e.printStackTrace()
            }
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
    private fun executeData(){
        SvParamsData(object :SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataExchange = Gson().fromJson(body,SvSubExchangeModels::class.java)
                    if (dataExchange.Status=="True"){
                        var  listExchange = dataExchange.Datarow
                        binding.rvExchange.apply {
                            adapter = SvAdapterExchangeSub(requireContext(),listExchange)
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
                SvConst.loge(TAG,"t $t")
            }
        }).getLoadData(SvURL.BASE_URL,SvURL.exchange,"")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        SvConst.clickAble = true
    }
}