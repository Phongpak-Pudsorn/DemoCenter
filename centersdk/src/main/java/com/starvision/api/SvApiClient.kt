package com.starvision.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SvApiClient {

    var retrofitAds : Retrofit? = null
    fun getBaseLink(url : String,port: String):Retrofit{ retrofitAds = Retrofit.Builder()
        .baseUrl(url+port)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        return retrofitAds!!}


}

