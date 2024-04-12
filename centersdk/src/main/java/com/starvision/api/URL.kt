package com.starvision.api

object URL {
    //SDK
    const val BASE_URL_SDK = "https://app.starvision.in.th"

//    const val URL_PROFILE = "https://app.starvision.in.th/api/myprofile/"
//    const val URL_LOGIN = "https://app.starvision.in.th/login/api/login_star.php"
//    const val URL_REGISTER = "https://app.starvision.in.th/login/api/register.php"
//    const val URL_TOPUP = https://starvision.in.th/apptopup/topup/
//    const val URL_FORGOTPASS = https://app.starvision.in.th/login/api/forgetpass.php
//    const val URL_DELETE_AND_RECOVERY = https://app.starvision.in.th/login/api/suspend.php
//    const val URL_CHECK_VERSION = https://app.starvision.in.th/api/checkversion   ?app=0  &os=android

    const val URL_CHECK_VERSION = "/api/checkversion"
    const val URL_TOPUP = "/apptopup/topup/"
    const val URL_LOGIN = "/login/api/login_star.php"
    const val URL_REGISTER = "/login/api/register.php"
    const val URL_PROFILE = "/api/myprofile"
    const val URL_CENTER = "/api/apicenter"
    const val URL_FORGOTPASS = "/login/api/forgetpass.php"
    const val URL_DELETE_AND_RECOVERY = "/login/api/suspend.php"

    const val BASE_URL = "https://starvision.in.th"
    const val BASE_URL_LOTTO= "https://lottery.starvision.in.th"

    //เลขเด็ดหวยดัง
    const val lotto_office = "action/file_json/suggest_"
    const val lotto_office_date = "/action/file_json/SuggestDate.json"

    //ตรวจหวย
    const val lotto_result =  "/getLottoResult/"
    const val lotto_total_result =  "/getLottoResult/result_date.json"
//    https://lottery.starvision.in.th:9943/getLottoResult/result_date.json
//    https://lottery.starvision.in.th:9943/+ getLottoResult/+ date + .jsons

    //12 ราศี
    const val zodiac ="https://goo.gl/dYNb25"

    //ราคาทอง
    const val gold_to_day = "https://lottery.starvision.in.th/gold/file_select_rate.php"

    //อัตราแลกเปลี่ยน
    const val exchange = "/mobileweb/appsmartdirect/exchangerate/serverweb/services/datajson/exchange_rate.json"

    //ราคาน้ำมัน
    const val oil_price = "/mobileweb/appsmartdirect/oil/serverweb/services/datajson/file_get_oil.json"

}