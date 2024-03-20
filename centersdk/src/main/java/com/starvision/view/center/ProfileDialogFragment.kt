package com.starvision.view.center

import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.starvision.api.*
import com.starvision.config.AESHelper
import com.starvision.config.MD5
import com.starvision.data.Const
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.center.models.ProfileModels
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ProfileDialogFragment : DialogFragment() {
    private val binding : PageFullProfileBinding by lazy { PageFullProfileBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName
    private val hashMap = kotlin.collections.HashMap<String?,String?>()
    private lateinit var viewModel : MainViewModel

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
        bindingObject()

        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val idx = AESHelper.encrypt("1001001002",Const.AES_KEY)
        val ts =  AESHelper.encrypt(timeStamp,Const.AES_KEY)
        val sign = MD5.CMD5("Starvision|$idx|CheckProfile|$ts")
//        val sign = "Starvision|$idx|CheckProfile|$ts"

        hashMap["idx"] = idx
        hashMap["ts"] = ts
        hashMap["sign"] = sign

        Const.loge(TAG,"sign : $sign")
        Const.loge(TAG,"idx : $idx")
        Const.loge(TAG,"ts : $ts")
//        Const.loge(TAG,"ถอด sign : "+AESHelper.decrypt("c2Ux1/9d5wBV4bY95M00O3WXNZA2jBENbch2XPmQz4s=",Const.AES_KEY))
//        Const.loge(TAG,"ถอด ts : "+AESHelper.decrypt(ts,Const.AES_KEY))
//        Const.loge(TAG,"ถอด idx : "+AESHelper.decrypt(idx,Const.AES_KEY))

        Const.loge(TAG, "hashMap : $hashMap")
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory)[MainViewModel::class.java]
        viewModel.pushData("/api/myprofile",hashMap)
        viewModel.myPushResponse.observe(this) { response ->
            Const.loge(TAG, "body : $response")
//            response.source()
            Const.loge(TAG, "response.source() : "+response.data.idx)
            Const.loge(TAG, "response.source() : "+response.data.avatar)
            Const.loge(TAG, "response.source() : "+response.data.name)
            Const.loge(TAG, "response.source() : "+response.data.coin)
        }



//        val service = ApiClient().getBaseLink(URL.BASE_URL_SDK,"").create(Api::class.java)
//        service.pushData("/api/myprofile",hashMap)
//
//
//        service.getData("/api/myprofile").enqueue(object : Callback<ProfileModels?>{
//            override fun onResponse(call: Call<ProfileModels?>, response: Response<ProfileModels?>) {
//                try {
//                    Const.loge(TAG,"onResponse url :"+call.request().url)
//                    Const.loge(TAG,"onResponse response :"+response.body()!!)
//                }catch (e : Exception){
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(call: Call<ProfileModels?>, t: Throwable) {
//                Const.loge(TAG,"onFailure url :"+call.request().url)
//                Const.loge(TAG, "onFailure t :$t")
//            }
//        })

        dialog!!.show()
    }

    private fun bindingObject(){
        binding.cardviewImgPro.setOnClickListener {}
        binding.tvName
        binding.tvCoin
        binding.tvIdx
        binding.tvTopup
        binding.tvLogout
    }

}