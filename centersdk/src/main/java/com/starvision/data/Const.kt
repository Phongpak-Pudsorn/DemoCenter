package com.starvision.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import java.util.*

object Const {
    const val AES_KEY = "47cbed84d5ad52e955621904170d2e6e"
    var KEY_PREFS_LOGIN = false

    fun loge(str : String,str2 : String){
        Log.e(str,str2)
    }

    @SuppressLint("HardwareIds")
    fun getUUID(context: Context): String? {
        return try {
            var identifier: String? = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            identifier = UUID.nameUUIDFromBytes(identifier!!.toByteArray(charset("utf8"))).toString()
            identifier
        } catch (e: java.lang.NullPointerException) {
            // Log.e("LOGIN SDK ", "getUUID NullPointerException");
            e.printStackTrace()
            ""
        } catch (e: Exception) {
            // Log.e("LOGIN SDK ", "getUUID Exception");
            e.printStackTrace()
            ""
        }
    }
}