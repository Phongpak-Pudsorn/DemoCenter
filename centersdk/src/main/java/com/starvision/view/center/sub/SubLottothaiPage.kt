package com.starvision.view.center.sub

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ParseException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupWindow
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
import com.starvision.luckygamesdk.databinding.PageLottothaiSubBinding
import com.starvision.view.center.sub.adapter.AdapterLottothaiSub
import com.starvision.view.center.sub.adapter.AdapterSpinnerCustom
import com.starvision.view.center.sub.models.SubLottothaiDateModels
import com.starvision.view.center.sub.models.SubLottothaiModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.util.*

class SubLottothaiPage : Fragment() {
    private val binding : PageLottothaiSubBinding by lazy { PageLottothaiSubBinding.inflate(layoutInflater) }
    private var adapterLottothaiSub : AdapterLottothaiSub? = null
    private var listDataDate : ArrayList<String>? = null
    private var listDataFd : ArrayList<String>? = null
    private val TAG = javaClass.simpleName
    private var listData = ArrayList<SubLottothaiModels>()
    private var listDataRow = ArrayList<SubLottothaiModels.NumberLot>()
    private var positionSpinner = 0
    private var intlistOff = 0
    private var intlistAny = 0
    private var jSonData = ""
    private var checkRefresh = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ExecuteDataDate()

        binding.tvNameApp.text = getString(R.string.text_name_app_lottothai)

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
        adapterLottothaiSub = AdapterLottothaiSub(listData)
        binding.reCycleView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        binding.reCycleView.adapter = adapterLottothaiSub
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
        ParamsData(object : ParamsData.PostLoadListener {
            override fun onSuccess(body: String) {
                val getApiLottoOffice = Gson().fromJson(body,SubLottothaiModels::class.java)
                if (getApiLottoOffice.Status == "True"){
                    for(i in getApiLottoOffice.Datarow.indices){
                        val dataRow = getApiLottoOffice.Datarow[i]
                        listDataRow.add(
                            SubLottothaiModels.NumberLot(dataRow.suggest_id, dataRow.suggest_date, dataRow.suggest_name, dataRow.top_second,
                                dataRow.bottom_second, dataRow.top_third, dataRow.creationdate, dataRow.modificationdate, dataRow.compare_lotto_resault))
                        listData.add(SubLottothaiModels(getApiLottoOffice.Status,getApiLottoOffice.ErrorCode,listDataRow))
                    }
                    binding.mProgressBar.visibility = View.GONE
                    binding.reCycleView.scrollToPosition(intlistOff)

                }
            }

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t : $t")
            }

        }).getLoadData(URL.BASE_URL_LOTTO,URL.lotto_office+date+".json",":9943")
    }

    private fun ExecuteDataDate() {
        ParamsData(object : ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataDate = Gson().fromJson(body,SubLottothaiDateModels::class.java)
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
                Const.loge(TAG,"t : $t")
            }
        }).getLoadData(URL.BASE_URL_LOTTO,URL.lotto_office_date,":9943")

    }
}