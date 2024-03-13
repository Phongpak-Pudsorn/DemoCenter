package com.starvision.api


import com.starvision.view.center.sub.models.SubLottothaiDateModels
import com.starvision.view.center.sub.models.SubLottothaiModels
import com.starvision.view.center.sub.models.SubLottothaiNumberModels
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

//    @GET("/appbannersdk/serverweb/data_json/{packageName}_Android_TH.json")
//    fun getDataAds(@Path("packageName") packageName : String): Call<AdsModel>

    @GET(URL.lotto_office+"{date}"+".json")
    fun getLottoOffice(@Path("date") date : String): Call<SubLottothaiModels>

    @GET(URL.lotto_office_result+"{date}"+".json")
    fun getLottoOfficeResult(@Path("date") date : String): Call<SubLottothaiNumberModels>

    @GET(URL.lotto_office_date)
    fun getLottoOfficeDate() : Call<SubLottothaiDateModels>
}