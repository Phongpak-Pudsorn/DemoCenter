package com.starvision.view.center.sub.models

data class SubLottothaiModels(val Status : String,
                        val ErrorCode : String?,
                        val Datarow : ArrayList<NumberLot>) {
    data class NumberLot(val suggest_id : Int,
                         val suggest_date : String,
                         val suggest_name : String,
                         val top_second : String,
                         val bottom_second : String,
                         val top_third : String,
                         val creationdate : String,
                         val modificationdate : String,
                         val compare_lotto_resault : Boolean)
}