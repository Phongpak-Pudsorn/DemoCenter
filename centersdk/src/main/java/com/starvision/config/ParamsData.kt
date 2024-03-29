package com.starvision.config

import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.data.Const
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParamsData(private var mPostLoadListener : PostLoadListener) {
    private val TAG = javaClass.simpleName

    interface PostLoadListener{
        fun onSuccess(body : String)
        fun onFailed(t: Throwable)
    }

    fun postLoadData(urlBase : String,path :String,port : String ,hashMap : HashMap<String?,String?>){
        val apiService = ApiClient().getBaseLink(urlBase,port).create(Api::class.java)
        apiService.postRequest(path,hashMap).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    mPostLoadListener.onSuccess(response.body()!!.string())
                }catch (e : Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    mPostLoadListener.onFailed(t)
                }catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun getLoadData(urlBase : String,path :String,port: String){
        val apiService = ApiClient().getBaseLink(urlBase, port).create(Api::class.java)
        apiService.getRequest(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    mPostLoadListener.onSuccess(response.body()!!.string())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    mPostLoadListener.onFailed(t)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

}