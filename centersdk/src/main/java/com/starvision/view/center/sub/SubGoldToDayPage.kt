package com.starvision.view.center.sub

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageGoldtodaySubBinding
import com.starvision.view.center.sub.models.SubGoldToDayModel
import com.starvision.view.center.sub.models.SubSmileLottoRewardModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubGoldToDayPage : DialogFragment() {
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
            Const.openAnotherApp(requireContext(),getString(R.string.goldToday_package))
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
        ParamsData(object : ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val listGold = Gson().fromJson(body,SubGoldToDayModel::class.java)
                if(listGold.Status == "True") {
                    //แท่ง
                    binding.tvPriceBuy.text = listGold.Datarow[0].gbbuy
                    binding.tvPriceSell.text = listGold.Datarow[0].gbsell
                    binding.tvProgressBuy.text = listGold.Datarow[0].gbb_change.toString()
                    binding.tvProgressSell.text = listGold.Datarow[0].gbs_change.toString()
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
                    binding.tvPriceBuy2.text = listGold.Datarow[0].gobuy
                    binding.tvPriceSell2.text = listGold.Datarow[0].gosell
                    binding.tvProgressBuy2.text = listGold.Datarow[0].gob_change.toString()
                    binding.tvProgressSell2.text = listGold.Datarow[0].gos_change.toString()
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
                    binding.progressBar.visibility = View.GONE
                    binding.linearLayout4.visibility = View.VISIBLE
                    binding.linearLayout3.visibility = View.VISIBLE
                }
            }

            override fun onFailed(t: Throwable) {
                Const.loge(TAG,"t $ $t")
            }
        }).getLoadData(URL.BASE_URL_LOTTO,URL.gold_to_day,"")

    }

}