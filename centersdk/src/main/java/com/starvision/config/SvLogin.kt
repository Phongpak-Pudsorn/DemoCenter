package com.starvision.config

import android.annotation.SuppressLint
import com.starvision.data.SvAppPreferencesLogin

@SuppressLint("StaticFieldLeak")
object SvLogin {
    private val appPref = SvAppPreferencesLogin
    private val mContext = SvMyApplication.appContext

    var isLogin : Boolean
    get() {
        var result : Boolean? = null
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_LOGIN,false) as Boolean
        return result
    }set(value) {
        appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_LOGIN, value)
    }
    var isRememberCheck : Boolean
    get() {
        var result : Boolean? = null
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK,false) as Boolean
        return result
    }set(value) {
        appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_REMEMBER_CHECK, value)
    }

    var UserID : String
    get(){
        var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_USERID,"").toString()
        return result
    }set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_USERID,value)

    var IDX : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_IDX,"").toString()
        return result
    }set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_IDX,value)

    var Name : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_NAME,"").toString()
        return result
    }set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_NAME,value)

    var Avatar : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_AVATAR,"").toString()
        return result
    }set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_AVATAR,value)

    var Coin : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_COIN,"").toString()
        return result
    }set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_COIN,value)

    var UserName : String
    get() {
        var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_REMEMBER_USER,"").toString()
        return result
    } set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_REMEMBER_USER,value)

    var Password : String
        get() { var result = ""
        result = appPref.getPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,"").toString()
        return result
    } set(value) = appPref.setPreferences(mContext,SvAppPreferencesLogin.KEY_PREFS_REMEMBER_PASSWORD,value)

}