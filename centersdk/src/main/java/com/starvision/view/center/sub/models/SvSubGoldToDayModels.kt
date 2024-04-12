package com.starvision.view.center.sub.models

data class SvSubGoldToDayModels(val Status : String,
                                val linkchart : String,
                                val Datarow : ArrayList<RowData>){

    data class RowData(val date_update : String,
                       val gbbuy : String,
                       val gbsell : String,
                       val gobuy : String,
                       val gosell : String,
                       val gbb_change : Int,
                       val gbs_change : Int,
                       val gob_change : Int,
                       val gos_change : Int,
                       val usd : String,
                       val goldspot : String,
                       val linkchart : String)
}

//{
//    "Status": "True",
//    "linkchart": "http://www.starvision.in.th/mobileweb/appsmartdirect/gold/serverweb/services/datajson/linkgrap.html",
//    "Datarow": [
//    {
//        "date_update": "2024-03-18 09:07:00",
//        "gbbuy": "36,550",
//        "gbsell": "36,650",
//        "gobuy": "35,899",
//        "gosell": "37,150",
//        "gbb_change": -50,
//        "gbs_change": -50,
//        "gob_change": -45,
//        "gos_change": -50,
//        "usd": " 35.95",
//        "goldspot": " 2,152.50",
//        "linkchart": "https://www.starvision.in.th/mobileweb/appsmartdirect/gold/serverweb/services/datajson/linkgrap.html"
//    },
