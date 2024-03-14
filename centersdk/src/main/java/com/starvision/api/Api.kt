package com.starvision.api


import com.starvision.view.center.sub.models.SubLottothaiDateModels
import com.starvision.view.center.sub.models.SubLottothaiModels
import com.starvision.view.center.sub.models.SubLottothaiNumberModels
import com.starvision.view.center.sub.models.SubSmileLottoRewardModels
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    // เลขเด็ดหวยดัง
    @GET(URL.lotto_office+"{date}"+".json")
    fun getLottoOffice(@Path("date") date : String): Call<SubLottothaiModels>
//    @GET(URL.lotto_office_result+"{date}"+".json")
//    fun getLottoOfficeResult(@Path("date") date : String): Call<SubLottothaiNumberModels>

    //ตรวจหวย
    @GET(URL.lotto_office_date)
    fun getLottoOfficeDate() : Call<SubLottothaiDateModels>
    @GET(URL.lotto_result+"{date}"+".json")
    fun getLottoResult(@Path("date") date : String): Call<SubSmileLottoRewardModels>
}