package com.starvision.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvParamsData
import com.starvision.models.SvCheckVersionModels
import java.text.SimpleDateFormat
import java.util.*

object SvConst {
    var clickAble = true
    var appPackage = ""
    const val AES_KEY = "47cbed84d5ad52e955621904170d2e6e"
    val timeStamp : String = SimpleDateFormat("HHmmssddMMyyyy").format(Date())
    var isReviewSDK = false
    var isSdkSDK = false
    var prioritySDK = ""
    var chkVersionSDK = ""
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
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val jsonData = Gson().fromJson(body,SvCheckVersionModels::class.java)
                isReviewSDK = jsonData.data.StatusServer.is_review
                isSdkSDK = jsonData.data.StatusServer.is_sdk
                prioritySDK = jsonData.data.Version.priority
                chkVersionSDK = jsonData.data.Version.current_version
            }

            override fun onFailed(t: Throwable) {
                t.printStackTrace()
            }

        }).getLoadData(SvURL.BASE_URL_SDK, SvURL.URL_CHECK_VERSION+"?"+"app=0"+"&os=android","")
    }

    fun checkStatusApp(app : String) : Boolean{
        var isReviewApp = false
        var isSdkApp = false
        var priorityApp = ""
        var chkVersionApp = ""
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val jsonData = Gson().fromJson(body,SvCheckVersionModels::class.java)
                isReviewApp = jsonData.data.StatusServer.is_review
                isSdkApp = jsonData.data.StatusServer.is_sdk
                priorityApp = jsonData.data.Version.priority
                chkVersionApp = jsonData.data.Version.current_version
            }

            override fun onFailed(t: Throwable) {
                t.printStackTrace()
            }

        }).getLoadData(SvURL.BASE_URL_SDK, SvURL.URL_CHECK_VERSION+"?"+"app=$app"+"&os=android","")
        return isReviewApp
    }
}