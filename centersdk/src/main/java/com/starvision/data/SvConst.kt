package com.starvision.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
    var isReview = false
    var isReviewSDK : Boolean? = null
    var isSdkSDK : Boolean? = null
    lateinit var appContext : Context
    const val AES_KEY = "47cbed84d5ad52e955621904170d2e6e"
    val timeStamp : String = SimpleDateFormat("HHmmssddMMyyyy").format(Date())
    fun loge(str : String,str2 : String){
        Log.e(str,str2)
    }

    private lateinit var mCheckListener : CheckListener
    interface CheckListener {
        fun onCheckIsSDK(isSdkSDK: Boolean)
        fun onCheckIsReview(isReviewSDK: Boolean)
    }
    fun setClickListener(listener : CheckListener) {
        mCheckListener = listener
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

    fun checkStatus(packageName : String){
        val app = getApp(packageName)
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val jsonData = Gson().fromJson(body,SvCheckVersionModels::class.java)
                isReviewSDK = jsonData.data.StatusServer.is_review
                isSdkSDK = jsonData.data.StatusServer.is_sdk
                val prioritySDK = jsonData.data.Version.priority
                val chkVersionSDK = jsonData.data.Version.current_version
                mCheckListener.onCheckIsSDK(isSdkSDK!!)
                mCheckListener.onCheckIsReview(isReviewSDK!!)
            }

            override fun onFailed(t: Throwable) {
                t.printStackTrace()
            }

        }).getLoadData(SvURL.BASE_URL_SDK, SvURL.URL_CHECK_VERSION+"?"+"app=$app"+"&os=android","")
    }
    private fun getApp(packageName: String):String{
        when(packageName){
            "com.starvision.lottothai" -> return "1"
            "com.smileapp.lottery" -> return "2"
            "com.smileapp.zodiac" -> return "4"
            "com.smileapp.goldprice" -> return "5"
            "com.smileapp.oil" -> return "6"
            "com.starvision.exchangerate" -> return "7"
        }
        return "0"
    }

    fun checkStatusApp(app : String) : Boolean{
        var isSdk = false
        SvParamsData(object : SvParamsData.PostLoadListener{
            override fun onSuccess(body: String) {
                val jsonData = Gson().fromJson(body,SvCheckVersionModels::class.java)
                isReview = jsonData.data.StatusServer.is_review
                isSdk = jsonData.data.StatusServer.is_sdk
            }

            override fun onFailed(t: Throwable) {
                t.printStackTrace()
            }

        }).getLoadData(SvURL.BASE_URL_SDK, SvURL.URL_CHECK_VERSION+"?"+"app=$app"+"&os=android","")
        return isSdk
    }
}