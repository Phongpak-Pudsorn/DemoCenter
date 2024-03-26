package com.starvision.api


import android.database.Observable
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.center.sub.models.*
import com.starvision.view.login.models.LoginModels
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {
    //อัตราแลกเปลี่ยน
    @GET(URL.exchange)
    fun getExchange() : Call<SubExchangeModel>
    //ราคาน้ำมัน
    @GET(URL.oil_price)
    fun getOil() : Call<SubOilModel>

    @POST()
    fun postRequest(@Url url : String, @Body body: java.util.HashMap<String?, String?>): Call<ResponseBody>

    @GET()
    fun getRequest(@Url url : String): Call<ResponseBody>

}