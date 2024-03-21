package com.starvision.view.login.models

data class LoginModels(val code : String,
                       val userid : String,
                       val message : String,
                       val SKey : String?,
                       val idx : String?,
//                       val data : Data?
){
    data class Data (val idx : String?,
                     val name : String?,
                     val avatar : String?,
                     val coin : Int?)
}

// Get
//  {
//        "code":"1101",
//        "userid":"st@1",
//        "message":"success",
//        "SKey":"8687c089ddbff8ebc2cc3059b83ab935",
//        "idx":"1001001000"
//   }