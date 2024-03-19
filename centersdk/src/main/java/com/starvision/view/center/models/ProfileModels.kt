package com.starvision.view.center.models

data class ProfileModels (val code : String,
                          val message : String,
                          val data : Data
){

    data class Data (val idx : String,
                     val name : String,
                     val avatar : String,
                     val coin : Int)
}


//{
//      "code": "101",
//      "message": "success",
//      "data": {
//          "idx": "1001001000",
//          "name": "Dummy ST.",
//          "avatar": "http://app.starvision.in.th/assets/images/pic-profile.jpg",
//          "coin": 500
//          }
//}