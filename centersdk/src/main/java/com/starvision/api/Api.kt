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

    // เลขเด็ดหวยดัง
    @GET(URL.lotto_office+"{date}"+".json")
    fun getLottoOffice(@Path("date") date : String): Call<SubLottothaiModels>
    @GET(URL.lotto_office_date)
    fun getLottoOfficeDate() : Call<SubLottothaiDateModels>

    //GolToDay
    @GET(URL.gold_to_day)
    fun getListGoldToDay() : Call<SubGoldToDayModel>

    //ตรวจหวย
    @GET(URL.lotto_total_result)
    fun getLottoTotalDate() : Call<SubSmileLottoDateModels>
    @GET(URL.lotto_result+"{date}"+".json")
    fun getLottoResult(@Path("date") date : String): Call<SubSmileLottoRewardModels>
    //อัตราแลกเปลี่ยน
    @GET(URL.exchange)
    fun getExchange() : Call<SubExchangeModel>
    //ราคาน้ำมัน
    @GET(URL.oil_price)
    fun getOil() : Call<SubOilModel>

    //Profile
    @GET()
    fun getData(@Url url : String) : Observable<ProfileModels>
    @FormUrlEncoded
    @POST()
    suspend fun pushData (
        @Url url : String,
        @FieldMap params : HashMap<String?,String?>
    ) : ProfileModels


    //Login
//    @POST("/login/api/login_star.php")
//    fun getLogin(@Body user: String?): Call<LoginModels?>?

}