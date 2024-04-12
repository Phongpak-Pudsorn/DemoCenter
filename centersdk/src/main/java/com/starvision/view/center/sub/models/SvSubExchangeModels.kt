package com.starvision.view.center.sub.models

data class SvSubExchangeModels(val Status:String,
                               val Update:String,
                               val BotName:String,
                               val BotBuy:String,
                               val BotSell:String,
                               val LastUpdate:String,
                               val Datarow:ArrayList<BankData>) {
    data class BankData(val DatarowBank:SubBankData){
        data class SubBankData(val Bank:String,
                               val Bank_full:String,
                               val Bank_full_th:String,
                               val Bank_link:String,
                               val Bank_ExchangeRate:ArrayList<ExchangeRateData>){
            data class ExchangeRateData(val currency:String,
                                        val currency_full:String,
                                        val buy:String,
                                        val sell:String){

            }

        }

    }
}