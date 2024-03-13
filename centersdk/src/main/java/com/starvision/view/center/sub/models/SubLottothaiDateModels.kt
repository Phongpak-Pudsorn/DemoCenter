package com.starvision.view.center.sub.models

data class SubLottothaiDateModels (val Status : String,
                                   val Datarow : ArrayList<Date>){
    data class Date(val suggestdate : String)
}

