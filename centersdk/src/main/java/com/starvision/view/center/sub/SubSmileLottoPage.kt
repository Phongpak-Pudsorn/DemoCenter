package com.starvision.view.center.sub

import android.content.Context
import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageSmilelottoSubBinding
import com.starvision.view.center.sub.models.SubLottothaiDateModels
import com.starvision.view.center.sub.models.SubSmileLottoRewardModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SubSmileLottoPage : Fragment() {
    private val binding : PageSmilelottoSubBinding by lazy { PageSmilelottoSubBinding.inflate(layoutInflater) }
    private lateinit var listLotto : SubSmileLottoRewardModels
    private var listDataDate : ArrayList<String>? = null
    private var sugDate = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executeDataDate()
    }

    private fun setObjViewLotto() {
        binding.mLnHome.visibility = View.VISIBLE
        binding.mLnHome.removeAllViews()
        val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = if (listLotto != null ) {
            if (!listLotto.form_new) {
                inflater.inflate(R.layout.layout_home_lotto_old, null, true)
            } else {
                inflater.inflate(R.layout.layout_home_lotto_new, null, true)
            }
        } else {
            inflater.inflate(R.layout.layout_home_lotto_new, null, true)
        }
        val layout_cut_lotto = view.findViewById<View>(R.id.layout_cut_lotto) as LinearLayout
        binding.mLnHome.addView(view)
        if (listLotto != null) {
            val mTvOne = view.findViewById<View>(R.id.mTvOne) as TextView
            val mTvTwo = view.findViewById<View>(R.id.mTvTwo) as TextView
            val mTvThree = view.findViewById<View>(R.id.mTvThree) as TextView
            val mTvfour = view.findViewById<View>(R.id.mTvfour) as TextView
            val mTvfive = view.findViewById<View>(R.id.mTvfive) as TextView
            val mTvOneBy = view.findViewById<View>(R.id.mTvOneBy) as TextView
            val mTvTwoEnd = view.findViewById<View>(R.id.mTvTwoEnd) as TextView
            val mTvThreeEnd = view.findViewById<View>(R.id.mTvThreeEnd) as TextView
            mTvOne.text = listLotto.first
            mTvTwo.text = listLotto.second
            mTvThree.text = listLotto.third
            mTvfour.text = listLotto.forth
            mTvfive.text = listLotto.fifth
            mTvOneBy.text = listLotto.near_one
            mTvTwoEnd.text = listLotto.last_two
            mTvThreeEnd.text = listLotto.last_three
            if (listLotto.form_new) {
                val mTvThreeStr = view.findViewById<View>(R.id.mTvThreeStr) as TextView
                mTvThreeStr.text = listLotto.first_three
                mTvThreeStr.textSize = 30f
            }
            layout_cut_lotto.visibility = View.VISIBLE
            mTvOne.textSize = 35f
            mTvTwoEnd.textSize = 35f
            mTvThreeEnd.textSize = 30f
            mTvOneBy.textSize = 25f
            mTvTwo.textSize = 23f
            mTvThree.textSize = 23f
            mTvfour.textSize = 23f
            mTvfive.textSize = 23f

            val mTvGlo = view.findViewById<View>(R.id.mTvGlo) as TextView
            mTvGlo.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.text_web_glo))))
            }
        }

    }

    private fun executeDataDate() {
        val getApiOfficeDate = ApiClient().getBaseLink(":9943").create(Api::class.java)
        getApiOfficeDate.getLottoOfficeDate().enqueue(object : Callback<SubLottothaiDateModels> {
            override fun onResponse(call: Call<SubLottothaiDateModels>, response: Response<SubLottothaiDateModels>) {
                try {
                    Log.e("TAG","url : "+call.request().url() )
                    val dataDate = response.body()!!
//                    listDataFd = ArrayList()
                    listDataDate = ArrayList()
                    var dateNew = ""
                    if (dataDate.Status == "True") {
//                        for (i in dataDate.Datarow.indices) {
                        sugDate = dataDate.Datarow[1].suggestdate
//                        listDataFd?.add(sugDate)
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
                        executeData()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(call: Call<SubLottothaiDateModels>, t: Throwable) {

            }
        })
    }

    private fun executeData() {
        val getApiOfficeDate = ApiClient().getBaseLink(":9943").create(Api::class.java)
        getApiOfficeDate.getLottoResult(sugDate).enqueue(object : Callback<SubSmileLottoRewardModels> {
            override fun onResponse(call: Call<SubSmileLottoRewardModels>, response: Response<SubSmileLottoRewardModels>) {
                Log.e("TAG","url : "+call.request().url() )
                val gsonfile = Gson().toJson(response.body()!!,SubSmileLottoRewardModels::class.java)
                listLotto = Gson().fromJson(gsonfile,SubSmileLottoRewardModels::class.java)
                setObjViewLotto()

            }
            override fun onFailure(call: Call<SubSmileLottoRewardModels>, t: Throwable) {

            }
        })
    }

}