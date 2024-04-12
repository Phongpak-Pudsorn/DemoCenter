package com.starvision.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SvApi {
    @POST()
    fun postRequest(@Url url : String, @Body body: java.util.HashMap<String?, String?>): Call<ResponseBody>

    @GET()
    fun getRequest(@Url url : String): Call<ResponseBody>

}