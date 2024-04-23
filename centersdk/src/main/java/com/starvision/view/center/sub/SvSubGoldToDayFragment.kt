package com.starvision.view.center.sub

import android.net.ParseException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvParamsData
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageGoldtodaySubBinding
import com.starvision.view.center.sub.models.SvSubGoldToDayModels
import java.text.SimpleDateFormat
import java.util.*

class SvSubGoldToDayFragment : DialogFragment() {
    private val binding : PageGoldtodaySubBinding by lazy { PageGoldtodaySubBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName

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
            SvConst.openAnotherApp(requireContext(),getString(R.string.goldToday_package))
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
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val listGold = Gson().fromJson(body,SvSubGoldToDayModels::class.java)
                if(listGold.Status == "True") {
                    //แท่ง
                    var gbbChange = ""
                    var gbsChange = ""
                    gbbChange = if (listGold.Datarow[0].gbb_change>0) {
                        "+" + listGold.Datarow[0].gbb_change.toString()
                    }else{
                        listGold.Datarow[0].gbb_change.toString()
                    }
                    gbsChange = if (listGold.Datarow[0].gbs_change>0){
                        "+" + listGold.Datarow[0].gbs_change.toString()
                    }else{
                        listGold.Datarow[0].gbs_change.toString()
                    }
                    binding.tvPriceBuy.text = listGold.Datarow[0].gbbuy
                    binding.tvPriceSell.text = listGold.Datarow[0].gbsell
                    binding.tvProgressBuy.text = gbbChange
                    binding.tvProgressSell.text = gbsChange
                    if(listGold.Datarow[0].gbb_change < 0){
                        binding.tvProgressBuy.setTextColor(requireActivity().getColor(R.color.red))
                        binding.imgBuy.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_red))
                    }else if(listGold.Datarow[0].gbb_change == 0){
                        binding.tvProgressBuy.setTextColor(requireActivity().getColor(R.color.grey_text))
                        binding.imgBuy.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_horizontal_rule_24))
                    }else{
                        binding.tvProgressBuy.setTextColor(requireActivity().getColor(R.color.green))
                        binding.imgBuy.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_green))
                        binding.imgBuy.rotationX = 180f
                    }
                    if(listGold.Datarow[0].gbs_change < 0){
                        binding.tvProgressSell.setTextColor(requireActivity().getColor(R.color.red))
                        binding.imgSell.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_red))
                    }else if(listGold.Datarow[0].gbs_change == 0){
                        binding.tvProgressSell.setTextColor(requireActivity().getColor(R.color.grey_text))
                        binding.imgSell.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_horizontal_rule_24))
                    }else{
                        binding.tvProgressSell.setTextColor(requireActivity().getColor(R.color.green))
                        binding.imgSell.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_green))
                        binding.imgSell.rotationX = 180f
                    }

                    //พรรณ
                    var gobChange = ""
                    var gosChange = ""
                    gobChange = if (listGold.Datarow[0].gob_change>0) {
                        "+" + listGold.Datarow[0].gob_change.toString()
                    }else{
                        listGold.Datarow[0].gob_change.toString()
                    }
                    gosChange = if (listGold.Datarow[0].gos_change>0){
                        "+" + listGold.Datarow[0].gos_change.toString()
                    }else{
                        listGold.Datarow[0].gos_change.toString()
                    }
                    binding.tvPriceBuy2.text = listGold.Datarow[0].gobuy
                    binding.tvPriceSell2.text = listGold.Datarow[0].gosell
                    binding.tvProgressBuy2.text = gobChange
                    binding.tvProgressSell2.text = gosChange
                    if(listGold.Datarow[0].gob_change < 0){
                        binding.tvProgressBuy2.setTextColor(requireActivity().getColor(R.color.red))
                        binding.imgBuy2.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_red))
                    }else if(listGold.Datarow[0].gob_change == 0){
                        binding.tvProgressBuy2.setTextColor(requireActivity().getColor(R.color.grey_text))
                        binding.imgBuy2.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_horizontal_rule_24))
                    }else{
                        binding.tvProgressBuy2.setTextColor(requireActivity().getColor(R.color.green))
                        binding.imgBuy2.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_green))
                        binding.imgBuy2.rotationX = 180f
                    }
                    if(listGold.Datarow[0].gos_change < 0){
                        binding.tvProgressSell2.setTextColor(requireActivity().getColor(R.color.red))
                        binding.imgSell2.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_red))
                    }else if(listGold.Datarow[0].gos_change == 0){
                        binding.tvProgressSell2.setTextColor(requireActivity().getColor(R.color.grey_text))
                        binding.imgSell2.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_horizontal_rule_24))
                    }else{
                        binding.tvProgressSell2.setTextColor(requireActivity().getColor(R.color.green))
                        binding.imgSell2.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_arrow_drop_down_24_green))
                        binding.imgSell2.rotationX = 180f
                    }
                    setTextTime(listGold.Datarow[0].date_update)
                    binding.progressBar.visibility = View.GONE
                    binding.linearLayout4.visibility = View.VISIBLE
                    binding.linearLayout3.visibility = View.VISIBLE
                }
            }

            override fun onFailed(t: Throwable) {
                SvConst.loge(TAG,"t $ $t")
            }
        }).getLoadData(SvURL.BASE_URL_LOTTO,SvURL.gold_to_day,"")

    }
    private fun setTextTime(dateUpdate: String) {
        val date = dateUpdate.split("\\ ").toTypedArray()
        var dateNew = ""
        try {
            val sdfOld = SimpleDateFormat("yyyy-MM-dd")
            val date2 = sdfOld.parse(date[0])
            val year = date2!!.year + 543 + 1900
            val sdfNew = SimpleDateFormat(" dd MMMM $year", Locale("th", "THA"))
            dateNew = sdfNew.format(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        binding.tvRound.text = getString(R.string.tv_gold_newest)+dateNew
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SvConst.clickAble = true
    }

}