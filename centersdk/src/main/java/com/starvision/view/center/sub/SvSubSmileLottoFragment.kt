package com.starvision.view.center.sub

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvParamsData
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageSmilelottoSubBinding
import com.starvision.view.center.sub.models.SvSubSmileLottoDateModels
import com.starvision.view.center.sub.models.SvSubSmileLottoRewardModels
import com.starvision.view.center.sub.util.SvDataBaseLotto
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SvSubSmileLottoFragment : DialogFragment() {
    private val binding : PageSmilelottoSubBinding by lazy { PageSmilelottoSubBinding.inflate(layoutInflater) }
    private lateinit var listLotto : SvSubSmileLottoRewardModels
    private lateinit var mHelper: SvDataBaseLotto
    private lateinit var mDb: SQLiteDatabase
    private lateinit var mCursor: Cursor
    private var listDataDate : ArrayList<String>? = null
    private var listDataFd : ArrayList<String>? = null
    private var positionSpinner = 1
    private var checkRefresh = false
    private var strQrCode = ""
    private var strNumberEdit = ""
    private var TAG = javaClass.simpleName

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
        executeDataDate()
        binding.tvNameApp.isSelected = true
        binding.tvDesApp.isSelected = true
        binding.mProgressBar.visibility = View.VISIBLE
        binding.cvApp.setOnClickListener {
            SvConst.openAnotherApp(requireContext(),getString(R.string.checklotto_package))
        }
        binding.imgBack.setOnClickListener {
            try {
                mClickListener.onClickBack()
            }catch (e : java.lang.Exception){
                e.printStackTrace()
            }
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    private fun setClick() {
        binding.mSpinner.text = listDataDate!![0]
        executeData(listDataFd!![0])
//        binding.mSpinner.setOnClickListener { spinnerPopupWindow(binding.mSpinner) }
    }

    private fun setObjViewLotto() {
        binding.mLnHome.visibility = View.VISIBLE
        binding.mProgressBar.visibility = View.GONE
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
            val second = listLotto.second.replace(" ","  ")
            val third = listLotto.third.replace(" ","  ")
            val forth = listLotto.forth.replace(" ","  ")
            val fifth = listLotto.fifth.replace(" ","  ")
            val nearOne = listLotto.near_one.replace(" ","  ")
            mTvOne.text = listLotto.first
            mTvTwo.text = second
            mTvThree.text = third
            mTvfour.text = forth
            mTvfive.text = fifth
            mTvOneBy.text = nearOne
            mTvTwoEnd.text = listLotto.last_two
            mTvThreeEnd.text = listLotto.last_three
            if (listLotto.form_new) {
                val mTvThreeStr = view.findViewById<View>(R.id.mTvThreeStr) as TextView
                mTvThreeStr.text = listLotto.first_three
                mTvThreeStr.textSize = 34f
            }
            layout_cut_lotto.visibility = View.VISIBLE
            mTvOne.textSize = 38f
            mTvTwoEnd.textSize = 36f
            mTvThreeEnd.textSize = 34f
            mTvOneBy.textSize = 29f
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
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                try {
                    val dataDate = Gson().fromJson(body,SvSubSmileLottoDateModels::class.java)
                    listDataFd = ArrayList()
                    listDataDate = ArrayList()
                    var dateNew = ""
//                    if (dataDate.Status == "True") {
                    for (i in dataDate.LottoDate.indices) {
                        val sugDate = dataDate.LottoDate[i]
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
                    }
                    setClick()
//                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t : $t")
            }

        }).getLoadData(SvURL.BASE_URL_LOTTO,SvURL.lotto_total_result,":9943")
    }

    private fun executeData(date: String) {
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                    listLotto = Gson().fromJson(body,SvSubSmileLottoRewardModels::class.java)
                    setObjViewLotto()
                    binding.btnCheck.setOnClickListener {
                        if (binding.mEditCheck.length() >= 6) {
                            mHelper =
                                SvDataBaseLotto(
                                    requireActivity()
                                )
                            mDb = mHelper.writableDatabase
                            val strSqlStatus =
                                (("SELECT * FROM " + SvDataBaseLotto.TABLE_NAME_CHECK) + " WHERE " + SvDataBaseLotto.CHE_DATE) + " = '" + listLotto.result_date + "'; "
                            mCursor = mDb.rawQuery(strSqlStatus, null)
                            if (mCursor.count == 0) {
                                mDb.execSQL(
                                    ((((((((((((("INSERT INTO " + SvDataBaseLotto.TABLE_NAME_CHECK) + " (" + SvDataBaseLotto.CHE_DATE) + ", " + SvDataBaseLotto.CHE_ONE) + ", " + SvDataBaseLotto.CHE_ONE_BY) + ", " + SvDataBaseLotto.CHE_TWO) + ", "
                                            + SvDataBaseLotto.CHE_TWO_END) + ", " + SvDataBaseLotto.CHE_THREE) + ", " + SvDataBaseLotto.CHE_FIRST_THREE_END) + ", " + SvDataBaseLotto.CHE_THREE_END) + ", "
                                            + SvDataBaseLotto.CHE_FOUR) + ", " + SvDataBaseLotto.CHE_FIVE) + ", " + SvDataBaseLotto.CHE_LINK_LOTTO) + ", "
                                            + SvDataBaseLotto.FORM_NEW) + ") " +
                                            "VALUES ('" + listLotto.result_date + "', + '" + listLotto.first + "', + '" + listLotto.near_one + "', + '" + listLotto.second + "', + '" + listLotto.last_two + "', + '" + listLotto.third
                                            + "', + '" + listLotto.first_three + "', + '" + listLotto.last_three + "', + '" + listLotto.forth + "', + '" + listLotto.fifth + "', + '" + listLotto.link_list_lotto_result + "', + '" + listLotto.form_new
                                            + "');"
                                )
                            }
                            val strCheckLotto = getCheck(requireContext(), listLotto.result_date, binding.mEditCheck.text.toString(), listLotto)
                            if (strCheckLotto != "") {
                                binding.mEditCheck.setText(strQrCode)
                                val strData_Lot = strCheckLotto.split("_")
                                mHelper =
                                    SvDataBaseLotto(
                                        requireActivity()
                                    )
                                mDb = mHelper.writableDatabase
                                if ((strData_Lot[2] == "P")) {
                                    mDb.execSQL(
                                        ((((((("INSERT INTO " + SvDataBaseLotto.TABLE_NAME_LOTTO_HISTORY) + " (" + SvDataBaseLotto.CHE_STRDATE_HIS) + ", " + SvDataBaseLotto.CHE_STR_RESULT_HIS) +
                                                ", " + SvDataBaseLotto.CHE_STR_NUMBER_HIS) + ", " + SvDataBaseLotto.CHE_STR_TEXT_HIS) + ") " +
                                                "VALUES ('" + listLotto.result_date) + "', + '" + strData_Lot[2] + "', + '" + strData_Lot[0] + "', + '" + strData_Lot[1] + "');")
                                    )
                                    val selectQuery = "SELECT * FROM " + SvDataBaseLotto.TABLE_NAME_LOTTO_HISTORY
                                    val cursor = mDb.rawQuery(selectQuery, null)
                                    cursor.moveToLast()
                                } else {
                                    mDb.execSQL(
                                        ((((((("INSERT INTO " + SvDataBaseLotto.TABLE_NAME_LOTTO_HISTORY) + " (" + SvDataBaseLotto.CHE_STRDATE_HIS) + ", " + SvDataBaseLotto.CHE_STR_RESULT_HIS) + ", " + SvDataBaseLotto.CHE_STR_NUMBER_HIS) + ", " + SvDataBaseLotto.CHE_STR_TEXT_HIS) + ") " +
                                                "VALUES ('" + listLotto.result_date) + "', + '" + strData_Lot[2] + "', + '" + strData_Lot[0] + "', + '" + strData_Lot[1] + "');")
                                    )
                                    val selectQuery = "SELECT * FROM " + SvDataBaseLotto.TABLE_NAME_LOTTO_HISTORY
                                    val cursor = mDb.rawQuery(selectQuery, null)
                                    cursor.moveToLast()
                                }

                                SvCheckLottoDialogFragment(strData_Lot[0],strData_Lot[1],strData_Lot[2],listDataDate!![0],true).show(childFragmentManager,"")

                                object : CountDownTimer(3000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {}
                                    override fun onFinish() {
                                        binding.mEditCheck.setText("")
                                        strNumberEdit = ""
                                        strQrCode = ""
                                    }
                                }.start()
                            }
                        }else{
                            SvCheckLottoDialogFragment("","","",listDataDate!![0],false).show(childFragmentManager,"")
                        }
                    }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t : $t")
            }
        }).getLoadData(SvURL.BASE_URL_LOTTO,SvURL.lotto_result+date+".json",":9943")

    }

    private fun getCheck(mContext: Context, strDateTime: String, strNumber: String, listLotto: SvSubSmileLottoRewardModels): String {
        var strCheckText = ""
        var strTextResult = ""
        var rusultt = false
        var rusultf = true
        val two_end = strNumber.substring(4, 6)
        val three_end = strNumber.substring(3, 6)
        val three_first_end = strNumber.substring(0, 3)
        try {
            if (strDateTime == listLotto.result_date) {
                val spl_one_by = listLotto.near_one.split(" ")
                val spl_three_end = listLotto.last_three.split(" ")
                val spl_first_three_end = listLotto.first_three.split(" ")
                val spl_two = listLotto.second.split(" ")
                val spl_three = listLotto.third.split(" ")
                val spl_four = listLotto.forth.split(" ")
                val spl_five = listLotto.fifth.split(" ")
                if (spl_one_by[0] == "" || spl_one_by[0] == "xxxxxx" || spl_one_by[0] == "XXXXXX") {
                    strCheckText =
                        strNumber + "_" + mContext.getString(R.string.sub_check_number_pause) + "_" + "P"
                } else {
                    if (strNumber == listLotto.first) {
                        strTextResult = if (strTextResult == "") {
                            mContext.getString(R.string.sub_check_number_result1)
                        } else {
                            strTextResult + "," + mContext.getString(R.string.sub_check_number_result1)
                        }

                    } else {
                        rusultf = false
                    }
                    if (two_end == listLotto.last_two) {
                        strTextResult = if (strTextResult == "") {
                            mContext.getString(R.string.sub_check_number_result2end)
                        } else {
                            strTextResult + "," + mContext.getString(R.string.sub_check_number_result2end)
                        }
                        rusultt = true
                    } else {
                        rusultf = false
                    }
                    for (t in spl_three_end.indices) {
                        val ttt = spl_three_end[t]
                        if (three_end == ttt) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result3end)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result3end)
                            }
                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    for (j in spl_first_three_end.indices) {
                        val tft = spl_first_three_end[j]
                        if (three_first_end == tft) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result3endup)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result3endup)
                            }
                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    for (b in spl_one_by.indices) {
                        val one_by = spl_one_by[b]
                        if (strNumber == one_by) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result1by)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result1by)
                            }
                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    for (tw in spl_two.indices) {
                        val two = spl_two[tw]
                        if (strNumber == two) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result2)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result2)
                            }
                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    for (fi in spl_three.indices) {
                        val five = spl_three[fi]
                        if (strNumber == five) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result3)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result3)
                            }

                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    for (fo in spl_four.indices) {
                        val four = spl_four[fo]
                        if (strNumber == four) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result4)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result4)
                            }
                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    for (fi in spl_five.indices) {
                        val five = spl_five[fi]
                        if (strNumber == five) {
                            strTextResult = if (strTextResult == "") {
                                mContext.getString(R.string.sub_check_number_result5)
                            } else {
                                strTextResult + "," + mContext.getString(R.string.sub_check_number_result5)
                            }
                            rusultt = true
                        } else {
                            rusultf = false
                        }
                    }
                    strCheckText = if (!rusultf && !rusultt) {
                        strNumber + "_" + mContext.getString(R.string.sub_check_number_result_out) + "_F"

                    } else {
                        strNumber + "_" + strTextResult + "_T"
                    }
                }
            } else {
                strCheckText =
                    strNumber + "_" + mContext.getString(R.string.sub_check_number_pause) + "_" + "P"
            }
        } catch (e: java.lang.Exception) {
            strCheckText =
                strNumber + "_" + mContext.getString(R.string.sub_check_number_pause) + "_" + "P"
        }
        return strCheckText
    }
    override fun onDestroyView() {
        super.onDestroyView()
        SvConst.clickAble = true
    }

}