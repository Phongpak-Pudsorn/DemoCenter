package com.starvision.view.center.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageGoldtodaySubBinding
import com.starvision.view.center.sub.models.SubGoldToDayModel
import com.starvision.view.center.sub.models.SubSmileLottoRewardModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubGoldToDayPage : Fragment() {
    private val binding : PageGoldtodaySubBinding by lazy { PageGoldtodaySubBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executeData()
    }

    private fun executeData(){
        val services = ApiClient().getBaseLink(URL.BASE_URL_LOTTO,":443").create(Api::class.java)
        services.getListGoldToDay().enqueue(object : Callback<SubGoldToDayModel>{
            override fun onResponse(call: Call<SubGoldToDayModel>, response: Response<SubGoldToDayModel>) {
                val gsonFile = Gson().toJson(response.body()!!, SubGoldToDayModel::class.java)
                val listGold = Gson().fromJson(gsonFile, SubGoldToDayModel::class.java)

                if(listGold.Status == "True") {
                    //แท่ง
                    binding.tvPriceBuy.text = listGold.Datarow[0].gbbuy
                    binding.tvPriceSell.text = listGold.Datarow[0].gbsell
                    binding.tvProgressBuy.text = listGold.Datarow[0].gbb_change.toString()
                    binding.tvProgressSell.text = listGold.Datarow[0].gbs_change.toString()
                    if(listGold.Datarow[0].gbb_change < 0){
                        binding.tvProgressBuy.setTextColor(requireActivity().getColor(R.color.red))
                        binding.tvProgressBuy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_arrow_drop_down_24_red, 0, 0,0)
                    }else{
                        binding.tvProgressBuy.setTextColor(requireActivity().getColor(R.color.green))
                        val dropDown = R.drawable.baseline_arrow_drop_down_24_green
                        dropDown.rotateLeft(180)
                        binding.tvProgressBuy.setCompoundDrawablesWithIntrinsicBounds(dropDown, 0, 0, 0)
                    }
                    if(listGold.Datarow[0].gbs_change < 0){
                        binding.tvProgressSell.setTextColor(requireActivity().getColor(R.color.red))
                        binding.tvProgressSell.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_arrow_drop_down_24_red, 0, 0,0)
                    }else{
                        binding.tvProgressSell.setTextColor(requireActivity().getColor(R.color.green))
                        val dropDown = R.drawable.baseline_arrow_drop_down_24_green
                        dropDown.rotateLeft(180)
                        binding.tvProgressSell.setCompoundDrawablesWithIntrinsicBounds(dropDown, 0, 0,0 )
                    }

                    //พรรณ
                    binding.tvPriceBuy2.text = listGold.Datarow[0].gobuy
                    binding.tvPriceSell2.text = listGold.Datarow[0].gosell
                    binding.tvProgressBuy2.text = listGold.Datarow[0].gob_change.toString()
                    binding.tvProgressSell2.text = listGold.Datarow[0].gos_change.toString()
                    if(listGold.Datarow[0].gob_change < 0){
                        binding.tvProgressBuy2.setTextColor(requireActivity().getColor(R.color.red))
                        binding.tvProgressBuy2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_arrow_drop_down_24_red, 0, 0,0)
                    }else{
                        binding.tvProgressBuy2.setTextColor(requireActivity().getColor(R.color.green))
                        val dropDown = R.drawable.baseline_arrow_drop_down_24_green
                        dropDown.rotateLeft(180)
                        binding.tvProgressBuy2.setCompoundDrawablesWithIntrinsicBounds(dropDown, 0, 0, 0)
                    }
                    if(listGold.Datarow[0].gos_change < 0){
                        binding.tvProgressSell2.setTextColor(requireActivity().getColor(R.color.red))
                        binding.tvProgressSell2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_arrow_drop_down_24_red, 0, 0,0)
                    }else{
                        binding.tvProgressSell2.setTextColor(requireActivity().getColor(R.color.green))
                        val dropDown = R.drawable.baseline_arrow_drop_down_24_green
                        dropDown.rotateLeft(180)
                        binding.tvProgressSell2.setCompoundDrawablesWithIntrinsicBounds(dropDown, 0, 0,0 )
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.linearLayout4.visibility = View.VISIBLE
                    binding.linearLayout3.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SubGoldToDayModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}