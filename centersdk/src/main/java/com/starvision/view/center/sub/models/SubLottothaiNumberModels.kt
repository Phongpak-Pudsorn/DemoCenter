package com.starvision.view.center.sub.models

data class SubLottothaiNumberModels(val Status : String,
                              val Datarow_TopSecond : ArrayList<DatarowTopSecond>,
                              val Datarow_BottomSecond : ArrayList<DatarowBottomSecond>,
                              val Datarow_TopThird : ArrayList<DatarowTopThird>){
    data class DatarowTopSecond (val suggest_date : String,
                                 val result : String,
                                 val count_suggest : String,
                                 val suggest_name : String)

    data class DatarowBottomSecond(val suggest_date : String,
                                   val result : String,
                                   val count_suggest : String,
                                   val suggest_name : String)

    data class DatarowTopThird (val suggest_date : String,
                                val result : String,
                                val count_suggest : String,
                                val suggest_name : String)
}
