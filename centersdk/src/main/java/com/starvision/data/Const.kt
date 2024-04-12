package com.starvision.data

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.starvision.api.URL
import com.starvision.config.ParamsData
import com.starvision.luckygamesdk.BuildConfig
import com.starvision.models.CheckVersionModels
import java.text.SimpleDateFormat
import java.util.*

object Const {
    var clickAble = true
    var appPackage = ""
    const val AES_KEY = "47cbed84d5ad52e955621904170d2e6e"
    val timeStamp : String = SimpleDateFormat("HHmmssddMMyyyy").format(Date())
    var isReview = false
    var isSdk = false
    var priority = ""
    var chkVersion = ""
//    var KEY_PREFS_FRAGMENT = ""
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

    fun openAnotherApp(context: Context, packageName: String) {
        try {
            var intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                // We found the activity now start the activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                // Bring user to the market or let them choose an app?
                intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.data = Uri.parse("market://details?id=$packageName")
                context.startActivity(intent)
            }
        }catch (e : java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun checkStatus(){
        ParamsData(object : ParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val jsonData = Gson().fromJson(body,CheckVersionModels::class.java)
                isReview = jsonData.data.StatusServer.is_review
                isSdk = jsonData.data.StatusServer.is_sdk
                priority = jsonData.data.Version.priority
                chkVersion = jsonData.data.Version.current_version

//                if(!isReview){
//                    // เซ็ตปิดปุ่ม
//
//                }
//                if(!isSdk){
//                    // เซ็ตปิดปุ่ม
//
//                }
//                if(priority == "True"){
//
//                }
            }

            override fun onFailed(t: Throwable) {
                t.printStackTrace()
            }

        }).getLoadData(URL.BASE_URL_SDK, URL.URL_CHECK_VERSION+"?"+"app=0"+"&os=android","")
    }
}