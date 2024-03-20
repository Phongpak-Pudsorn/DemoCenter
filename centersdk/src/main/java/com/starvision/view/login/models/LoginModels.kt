package com.starvision.view.login.models

data class LoginModels(val code : String,
                       val userid : String,
                       val message : String,
                       val data : Data?
){
    data class Data (val idx : String,
                     val name : String,
                     val avatar : String,
                     val coin : Int)
}
