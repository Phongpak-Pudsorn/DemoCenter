package com.starvision.data

import android.annotation.SuppressLint
import android.view.View
import com.starvision.config.SvLogin

@SuppressLint("StaticFieldLeak")
object SvGetViews {
    private var mContext = SvConst.appContext
    private val appPref = SvAppPreferencesLogin
//    private var bannerSmall : Int? = null
//
//    fun setBannerSmall (view: Int){
//        bannerSmall = view
//    }
//
//    fun getBannerSmall () : Int{
//        return bannerSmall!!
//    }

    var bannerSmall : Int
        get() {
            val result: Int? = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_BANNERSMALL,0) as Int?
            return result!!
        }set(value) {
        appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_BANNERSMALL, value)
    }


}