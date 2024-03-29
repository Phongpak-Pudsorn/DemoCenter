package com.starvision.data

import android.app.Activity
import android.content.Context
import android.os.Build
import java.util.*

object AppPreferencesLogin {

    private const val APP_SHARED_PREFS = "AppPreferences_Login"

    //get from check Remember
    const val KEY_PREFS_REMEMBER_CHECK = "KEY_PREFS_REMEMBER_CHECK"
    const val KEY_PREFS_REMEMBER_USER = "KEY_PREFS_REMEMBER_USER"
    const val KEY_PREFS_REMEMBER_PASSWORD = "KEY_PREFS_REMEMBER_PASSWORD"
    const val KEY_PREFS_LOGIN = "KEY_PREFS_LOGIN"

    //get from login
    const val KEY_PREFS_USERID = "KEY_PREFS_USERID"
    const val KEY_PREFS_SKEY = "KEY_PREFS_SKEY"
    const val KEY_PREFS_IDX = "KEY_PREFS_IDX"

    //get from checkProfile
    const val KEY_PREFS_NAME = "KEY_PREFS_NAME"
    const val KEY_PREFS_AVATAR = "KEY_PREFS_AVATAR"
    const val KEY_PREFS_COIN = "KEY_PREFS_COIN"
//    const val KEY_PREFS_HASH_LOGIN = "KEY_PREFS_HASH_LOGIN"

    //get from luckygame center
    const val KEY_PREFS_LUCKY_GAME = "KEY_PREFS_LUCKY_GAME"

    fun setPreferences(mContext: Context,key: String, objects: Any){
        val preferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE)
        val prefer = preferences.edit()
        when (objects) {
            is String -> {
                val encoded: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    Base64.getEncoder().encodeToString(objects.toString().toByteArray())
                } else {
                    android.util.Base64.encodeToString(objects.toString().toByteArray(),0)
                }
                prefer.putString(key, encoded)
//                Log.e("encoded",""+encoded)
            }
            is Int -> {
                prefer.putInt(key, objects)
            }
            is Boolean -> {
                prefer.putBoolean(key, objects)
            }
            is Float -> {
                prefer.putFloat(key, objects as Float)
            }
            is Long -> {
                prefer.putLong(key, objects as Long)
            }
        }
        prefer.apply()
    }

    fun getPreferences(mContext: Context, key: String, objects: Any): Any? {
        val preferences = mContext.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE)
        when (objects) {
            is String -> {
                val pref = preferences.getString(key, objects)
                val decoded = try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        String(Base64.getDecoder().decode(pref))
                    }
                    else {
                        android.util.Base64.decode(pref,0)
                    }
                    //.try ↓
                } catch (e: Exception) {
                    pref
                }
//                Log.e("decoded", "pref $pref")
//                Log.e("decoded",""+decoded)
                return decoded

            }
            is Int -> {
                return preferences.getInt(key, objects)
            }
            is Boolean -> {
                return preferences.getBoolean(key, objects)
            }
            is Float -> {
                return preferences.getFloat(key, objects)
            }
            is Long -> {
                return preferences.getLong(key, objects)
            }
            else -> return null
        }
    }
}