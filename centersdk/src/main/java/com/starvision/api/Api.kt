package com.starvision.api


import com.starvision.view.center.sub.models.*
import com.starvision.view.login.models.ProfileModels
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    // เลขเด็ดหวยดัง
    @GET(URL.lotto_office+"{date}"+".json")
    fun getLottoOffice(@Path("date") date : String): Call<SubLottothaiModels>
    @GET(URL.lotto_office_date)
    fun getLottoOfficeDate() : Call<SubLottothaiDateModels>


    //ตรวจหวย
    @GET(URL.lotto_total_result)
    fun getLottoTotalDate() : Call<SubSmileLottoDateModels>
    @GET(URL.lotto_result+"{date}"+".json")
    fun getLottoResult(@Path("date") date : String): Call<SubSmileLottoRewardModels>

    //Profile
    @POST("/api/myprofile")
    fun addUser(@Body addUser: String?): Call<ProfileModels?>?

}