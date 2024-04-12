package com.starvision.models

data class SvCheckVersionModels(val code : String,
                                val message : String,
                                val data : DataRow){

    data class DataRow(val Status : String,
                       val StatusServer : DataStatusServer,
                       val Version : DataVersion){

        data class DataStatusServer(val status_id : String,
                                    val message : String,
                                    val active : Boolean,
                                    val creationdate : String,
                                    val modificationdate : String,
                                    val is_sdk : Boolean,
                                    val is_review : Boolean,)

        data class DataVersion(val id : Int,
                               val app : String,
                               val appname : String,
                               val message_alert_update_version : String,
                               val current_version : String,
                               val priority : String,
                               val link : String,
                               val creationdate : String,
                               val modificationdate : String,
                               val platform : String,
                               val youtube_active : Boolean,
                               val banner_status : String,
                               val popup_status : String,
                               val popup_size : String,
                               val modify_by : String,
                               val news_active : Boolean,)
    }
}

// {
//    "code": "1101",
//    "message": "Success",
//    "data": {
//         "Status": "True",
//         "StatusServer": {
//             "status_id": 1,
//             "message": "Server is temporarily closed for maintenance.",
//             "active": true,
//             "creationdate": "2014-03-27 09:33:21",
//             "modificationdate": "2014-03-27 09:33:21",
//             "is_sdk": true,
//             "is_review": true
//         },
//         "Version": {
//             "id": 0,
//             "app": "starvision",
//             "appname": "StarVision",
//             "message_alert_update_version": "กรุณาอัพเดทเวอร์ชั่นล่าสุด V1.0 ค่ะ",
//             "current_version": "1.0",
//             "priority": "True",
//             "link": "https://apps.apple.com/app/id990516432",
//             "creationdate": "2015-06-05 10:12:21.284271",
//             "modificationdate": "2017-11-06 08:49:37.134495",
//             "platform": "android",
//             "youtube_active": true,
//             "banner_status": "admob",
//             "popup_status": "admob",
//             "popup_size": "big",
//             "modify_by": "คุณสิด",
//             "news_active": false
//         }
//     }
// }
