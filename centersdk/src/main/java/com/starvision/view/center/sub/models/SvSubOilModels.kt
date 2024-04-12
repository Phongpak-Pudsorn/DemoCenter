package com.starvision.view.center.sub.models

data class SvSubOilModels(val Status:String,
                          val Datarow:OilPriceData){
    data class OilPriceData(val graph:ArrayList<String>,
                            val data:TodayData){
        data class TodayData(val today_date:String,
                             val tomorrow_date:String,
                             val price_today:ArrayList<StationData>){
            data class StationData(val oil:String,
                                   val oil_merc_name:String,
                                   val image:String,
                                   val data:ArrayList<OilTodayData>){
                data class OilTodayData(var id:String = "",
                                        val type:String,
                                        val name:String,
                                        val diff:String,
                                        val today:String,
                                        val tomorrow:String){

                }

            }

        }

    }
}
