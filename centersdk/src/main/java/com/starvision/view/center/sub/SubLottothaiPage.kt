package com.starvision.view.center.sub

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ParseException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageLottothaiSubBinding
import com.starvision.view.center.sub.adapter.AdapterLottothaiSub
import com.starvision.view.center.sub.adapter.AdapterSpinnerCustom
import com.starvision.view.center.sub.models.SubLottothaiDateModels
import com.starvision.view.center.sub.models.SubLottothaiModels
import com.starvision.view.center.sub.models.SubLottothaiNumberModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        binding.reCycleView.visibility = View.VISIBLE
        binding.mProgressBar.visibility = View.GONE
        binding.mLvOffice.isRefreshing = false
        binding.mLvOffice.setOnChildScrollUpCallback { parent, child ->
                            Log.e(TAG,"SwipeRefreshLayout parent : $parent")
                            Log.e(TAG,"SwipeRefreshLayout child : $child")
            false
        }
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
        binding.mSpinner.text = listDataDate!![0]
//        if (chkInternet!!.isOnline) {
            ExecuteData(listDataFd!![0])
//        }
        binding.mSpinner.setOnClickListener { spinnerPopupWindow(binding.mSpinner) }
    }

    private fun spinnerPopupWindow(anchorView: View) {
        val layout = layoutInflater.inflate(R.layout.layout_listview, null)
        val popup = PopupWindow(requireContext())
        val adp = AdapterSpinnerCustom(requireContext(), listDataDate!!)
        val listView = layout.findViewById<ListView>(R.id.listView)
        listView.adapter = adp
        if(checkRefresh){
            listView.setSelection(positionSpinner + 1)
        }else{
            listView.setSelection(1)
        }
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                positionSpinner = i
//                if (chkInternet!!.isOnline) {
                    checkRefresh = true
                    Log.e(TAG,"spinnerPopupWindow isOnline : "+listDataFd!![i])
                    ExecuteData(listDataFd!![i])
//                }
                binding.mSpinner.text = listDataDate!![i]
                popup.dismiss()
            }
        popup.contentView = layout

        // Set content width and height
        popup.height = WindowManager.LayoutParams.MATCH_PARENT
        popup.width = WindowManager.LayoutParams.MATCH_PARENT
        // Closes the popup window when touch outside of it - when looses focus
        popup.isOutsideTouchable = true
        popup.isFocusable = true
        // Show anchored to button
        popup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.showAsDropDown(anchorView)
    }

    private fun ExecuteData(date: String) {
        intlistOff = 0
        intlistAny = 0
        listData.clear()
        listDataRow.clear()
        binding.mProgressBar.visibility = View.VISIBLE

        val getApiOffice = ApiClient().getBaseLink(":9943").create(Api::class.java)
        getApiOffice.getLottoOffice(date).enqueue(object : Callback<SubLottothaiModels> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<SubLottothaiModels>, response: Response<SubLottothaiModels>) {
                val getApiLottoOffice = response.body()!!
                Log.e(TAG, "getApiLottoOffice : $getApiLottoOffice")
                Log.e(TAG,"URL : "+call.request().url())
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

            override fun onFailure(call: Call<SubLottothaiModels>, t: Throwable) {
                Log.e("OfficeFragment", "Load Api onFailure : $t")
            }
        })
        getApiOffice.getLottoOfficeResult(date).enqueue(object : Callback<SubLottothaiNumberModels> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<SubLottothaiNumberModels>, response: Response<SubLottothaiNumberModels>) {
                val dataResult = response.body()!!
                Log.e(TAG,"URL : "+call.request().url())
                if (dataResult.Status == "True"){
                    jSonData = Gson().toJson(response.body()!!,SubLottothaiNumberModels::class.java)
                    binding.mProgressBar.visibility = View.GONE
                    adapterLottothaiSub!!.notifyDataSetChanged()
                    binding.reCycleView.scrollToPosition(intlistOff)
                }
            }

            override fun onFailure(call: Call<SubLottothaiNumberModels>, t: Throwable) {
                Log.e(TAG,"Result Api onFailure")
            }
        })
    }

    private fun ExecuteDataDate() {
        val getApiOfficeDate = ApiClient().getBaseLink(":9943").create(Api::class.java)
        getApiOfficeDate.getLottoOfficeDate().enqueue(object : Callback<SubLottothaiDateModels> {
            override fun onResponse(call: Call<SubLottothaiDateModels>, response: Response<SubLottothaiDateModels>) {
                try {
                    val dataDate = response.body()!!
                Log.e(TAG,"url : "+call.request().url())
                Log.e("OfficeFragment", "dataDate : $dataDate")
                    listDataFd = ArrayList()
                    listDataDate = ArrayList()
                    var dateNew = ""
                    if (dataDate.Status == "True") {
                        for (i in dataDate.Datarow.indices) {
                            val sugDate = dataDate.Datarow[i].suggestdate
                            listDataFd?.add(sugDate)
                            try {
                                val string = sugDate
                                val sdfOld = SimpleDateFormat("yyyy-MM-dd")
                                val date2 = sdfOld.parse(string)
                                val year = date2!!.year + 543 + 1900
                                val sdfNew = SimpleDateFormat(" dd MMMM $year", Locale("th", "THA"))
                                dateNew = sdfNew.format(date2)
                                Log.e(TAG, "dateNew : $dateNew")
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            } catch (e: java.text.ParseException) {
                                e.printStackTrace()
                            }
                            listDataDate?.add("  " + resources.getString(R.string.text_date_off) + dateNew)
                        }
                        setClick()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(call: Call<SubLottothaiDateModels>, t: Throwable) {
                Log.e(TAG, "Load Api Office Date : onFailure $t")
            }
        })
    }
}