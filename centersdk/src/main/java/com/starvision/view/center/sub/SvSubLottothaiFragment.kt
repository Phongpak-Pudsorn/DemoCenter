package com.starvision.view.center.sub

import android.net.ParseException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageLottothaiSubBinding
import com.starvision.view.center.sub.adapter.SvAdapterLottothaiSub
import com.starvision.view.center.sub.models.SvSubLottothaiDateModels
import com.starvision.view.center.sub.models.SvSubLottothaiModels
import java.text.SimpleDateFormat
import java.util.*

class SvSubLottothaiFragment : DialogFragment() {
    private val binding : PageLottothaiSubBinding by lazy { PageLottothaiSubBinding.inflate(layoutInflater) }
    private var adapterLottothaiSub : SvAdapterLottothaiSub? = null
    private var listDataDate : ArrayList<String>? = null
    private var listDataFd : ArrayList<String>? = null
    private val TAG = javaClass.simpleName
    private var listData = ArrayList<SvSubLottothaiModels>()
    private var listDataRow = ArrayList<SvSubLottothaiModels.NumberLot>()
    private var intlistOff = 0
    private var intlistAny = 0
    private var checkRefresh = false

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
        ExecuteDataDate()
        binding.cvApp.setOnClickListener {
            SvConst.openAnotherApp(requireContext(),getString(R.string.lotto_package))
        }
        binding.tvNameApp.isSelected = true
        binding.tvDesApp.isSelected = true
        binding.reCycleView.visibility = View.VISIBLE
        binding.mLvOffice.isRefreshing = false
        binding.mLvOffice.setOnChildScrollUpCallback { parent, child -> false }
        binding.mLvOffice.setOnRefreshListener {
            binding.reCycleView.visibility = View.GONE
            checkRefresh = false
            ExecuteDataDate()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.reCycleView.visibility = View.VISIBLE
                binding.mLvOffice.isRefreshing = false
            },500)
        }
        adapterLottothaiSub = SvAdapterLottothaiSub(listData)
        binding.reCycleView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        binding.reCycleView.adapter = adapterLottothaiSub
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

    private fun setClick() {
        binding.tvRoundLot.text = listDataDate!![0]
        ExecuteData(listDataFd!![0])
    }

    private fun ExecuteData(date: String) {
        intlistOff = 0
        intlistAny = 0
        listData.clear()
        listDataRow.clear()
        binding.mProgressBar.visibility = View.VISIBLE
        SvParamsData(object : SvParamsData.PostLoadListener {
            override fun onSuccess(body: String) {
                val getApiLottoOffice = Gson().fromJson(body,SvSubLottothaiModels::class.java)
                if (getApiLottoOffice.Status == "True"){
                    for(i in getApiLottoOffice.Datarow.indices){
                        val dataRow = getApiLottoOffice.Datarow[i]
                        listDataRow.add(
                            SvSubLottothaiModels.NumberLot(dataRow.suggest_id, dataRow.suggest_date, dataRow.suggest_name, dataRow.top_second,
                                dataRow.bottom_second, dataRow.top_third, dataRow.creationdate, dataRow.modificationdate, dataRow.compare_lotto_resault))
                        listData.add(SvSubLottothaiModels(getApiLottoOffice.Status,getApiLottoOffice.ErrorCode,listDataRow))
                    }
                    binding.mProgressBar.visibility = View.GONE
                    binding.reCycleView.scrollToPosition(intlistOff)

                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t : $t")
            }

        }).getLoadData(SvURL.BASE_URL_LOTTO,SvURL.lotto_office+date+".json",":9943")
    }

    private fun ExecuteDataDate() {
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataDate = Gson().fromJson(body,SvSubLottothaiDateModels::class.java)
                    listDataFd = ArrayList()
                    listDataDate = ArrayList()
                    var dateNew = ""
                    if (dataDate.Status == "True") {
//                        for (i in dataDate.Datarow.indices) {
                        val sugDate = dataDate.Datarow[0].suggestdate
                        listDataFd?.add(sugDate)
                        try {
                            val string = sugDate
                            val sdfOld = SimpleDateFormat("yyyy-MM-dd")
                            val date2 = sdfOld.parse(string)
                            val year = date2!!.year + 543 + 1900
                            val sdfNew = SimpleDateFormat(" dd MMMM $year", Locale("th", "THA"))
                            dateNew = sdfNew.format(date2)
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                        listDataDate?.add("  " + resources.getString(R.string.text_date_off) + dateNew)
//                        }
                        setClick()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t : $t")
            }
        }).getLoadData(SvURL.BASE_URL_LOTTO,SvURL.lotto_office_date,":9943")

    }
    override fun onDestroyView() {
        super.onDestroyView()
        SvConst.clickAble = true
    }
}