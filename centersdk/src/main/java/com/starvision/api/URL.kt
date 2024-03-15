package com.starvision.api

object URL {
    //SDK
    const val BASE_URL_SDK = "http://app.starvision.in.th"
    const val URL_PROFILE = "http://app.starvision.in.th/api/myprofile"


    var BASE_URL = "https://starvision.in.th"

    //เลขเด็ดหวยดัง
    const val BASE_URL_LOTTO= "https://lottery.starvision.in.th"
    const val lotto_office = "action/file_json/suggest_"
    const val lotto_office_date = "/action/file_json/SuggestDate.json"

    //ตรวจหวย
    const val lotto_result =  "/getLottoResult/"
    const val lotto_total_result =  "/getLottoResult/result_date.json"
//    https://lottery.starvision.in.th:9943/getLottoResult/result_date.json
//    https://lottery.starvision.in.th:9943/+ getLottoResult/+ date + .jsons

}