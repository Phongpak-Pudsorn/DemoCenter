package com.starvision.api

import com.starvision.view.center.models.ProfileModels

class Repository {

    suspend fun pushData(url:String, params : HashMap<String?,String?>) : ProfileModels{
        return ApiClient().getBaseLink(URL.BASE_URL_SDK,"").create(Api::class.java).pushData(url,params)
    }
}