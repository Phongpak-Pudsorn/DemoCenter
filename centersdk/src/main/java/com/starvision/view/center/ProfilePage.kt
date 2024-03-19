package com.starvision.view.center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.config.AESHelper
import com.starvision.config.MD5
import com.starvision.data.Const
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.center.models.ProfileModels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProfilePage : DialogFragment() {
    private val binding : PageFullProfileBinding by lazy { PageFullProfileBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val idx = AESHelper.encrypt("1001001000",Const.AES_KEY)
        val ts =  AESHelper.encrypt(timeStamp,Const.AES_KEY)
        val sign = MD5.CMD5("Starvision|{$idx}|CheckProfile|{$ts}")
        Const.loge(TAG,"sign : $sign")
        Const.loge(TAG,"idx : $idx")
        Const.loge(TAG,"ts : $ts")
//        Const.loge(TAG,"ถอด sign : "+AESHelper.decrypt(sign!!,"47cbed84d5ad52e955621904170d2e6e"))
        Const.loge(TAG,"ถอด ts : "+AESHelper.decrypt(ts,Const.AES_KEY))
        Const.loge(TAG,"ถอด idx : "+AESHelper.decrypt(idx,Const.AES_KEY))
        val service = ApiClient().getBaseLink(URL.BASE_URL_SDK,":443").create(Api::class.java)
        service.getProfile(sign!!)!!.enqueue(object : Callback<ProfileModels?> {
//        service.getProfile("idx:$idx,ts:$ts,sign:$sign")!!.enqueue(object : Callback<ProfileModels?> {
            override fun onResponse(call: Call<ProfileModels?>, response: Response<ProfileModels?>) {
                try {
                    Const.loge(TAG," onResponse : "+call.request().url())
                    Const.loge(TAG," onResponse response: "+response.body()!!)
                    val gsonFile = Gson().toJson(response.body()!!, ProfileModels::class.java)
                    val listProfile = Gson().fromJson(gsonFile, ProfileModels::class.java)
                    binding.tvName.text = listProfile.data.name
                    binding.tvCoin.text = listProfile.data.coin.toString()
                    Glide.with(requireContext()).load(listProfile.data.avatar).into(binding.imgProfile)
                    binding.tvIdx.text = listProfile.data.idx
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ProfileModels?>, t: Throwable) {
                Const.loge(TAG," onFailure : "+call.request().url())
                Const.loge(TAG," onFailure : $t")
            }
        })

        binding.cardviewImgPro.setOnClickListener {}
        binding.tvName
        binding.tvCoin
        binding.tvIdx
        binding.tvTopup
        binding.tvLogout

        dialog!!.show()
    }

}