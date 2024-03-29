package com.starvision.config

import android.annotation.SuppressLint
import com.starvision.data.AppPreferencesLogin

@SuppressLint("StaticFieldLeak")
object Login {
    private val appPref = AppPreferencesLogin
    private val mContext = MyApplication.appContext

    val isLogin : Boolean
    get() {
        var result : Boolean? = null
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_LOGIN,false) as Boolean
        return result
    }

    val isRememberCheck : Boolean
    get() {
        var result : Boolean? = null
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,false) as Boolean
        return result
    }

    val getUserID : String
    get(){
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_USERID,"").toString()
        return result
    }
    val getSKey : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_SKEY,"").toString()
        return result
    }
    val getIDX : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_IDX,"").toString()
        return result
    }
    val getName : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_NAME,"").toString()
        return result
    }
    val getAvatar : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_AVATAR,"").toString()
        return result
    }
    val getCoin : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_COIN,"").toString()
        return result
    }
    val getUserName : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_REMEMBER_USER,"").toString()
        return result
    }
    val getPassword : String
        get() { var result = ""
        result = appPref.getPreferences(mContext,AppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,"").toString()
        return result
    }


}