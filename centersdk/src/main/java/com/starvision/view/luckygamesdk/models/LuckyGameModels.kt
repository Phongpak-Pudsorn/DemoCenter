package com.starvision.view.luckygamesdk.models

data class LuckyGameModels(val code : String,
                           val message : String,
                           val data : ArrayList<DataGame>){

    data class DataGame(val luckgameId:String,
                        val luckgameTitle:String,
                        val luckgameUrl:String)

}


//{
//    "code": "404",
//    "message": "No Information",
//    "data": null
//}