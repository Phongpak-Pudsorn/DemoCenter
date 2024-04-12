package com.starvision.config

import com.starvision.api.SvApi
import com.starvision.api.SvApiClient
import com.starvision.data.SvConst
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SvParamsData(private var mPostLoadListener : PostLoadListener) {
    private val TAG = javaClass.simpleName

    interface PostLoadListener{
        fun onSuccess(body : String)
        fun onFailed(t: Throwable)
    }

    fun postLoadData(urlBase : String,path :String,port : String ,hashMap : HashMap<String?,String?>){
        SvConst.loge(TAG, "link : $urlBase$path")
        SvConst.loge(TAG, "hashmap : $hashMap")
        val svApiService = SvApiClient().getBaseLink(urlBase,port).create(SvApi::class.java)
        svApiService.postRequest(path,hashMap).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val result = response.body()!!.string()
                    SvConst.loge(TAG,"response code : "+response.code())
                    SvConst.loge(TAG,"response message : "+response.message())
                    SvConst.loge(TAG, "response : $result")
                    mPostLoadListener.onSuccess(result)
                }catch (e : Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    SvConst.loge(TAG,"response : "+t.message)
                    mPostLoadListener.onFailed(t)
                }catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun getLoadData(urlBase : String,path :String,port: String){
        SvConst.loge(TAG, "link : $urlBase$path")
        val svApiService = SvApiClient().getBaseLink(urlBase, port).create(SvApi::class.java)
        svApiService.getRequest(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val result = response.body()!!.string()
                    SvConst.loge(TAG,"response code : "+response.code())
                    SvConst.loge(TAG,"response message : "+response.message())
                    SvConst.loge(TAG, "response : $result")
                    mPostLoadListener.onSuccess(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    SvConst.loge(TAG,"response : "+t.message)
                    mPostLoadListener.onFailed(t)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

}