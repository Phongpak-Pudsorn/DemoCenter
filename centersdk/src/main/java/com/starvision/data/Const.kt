package com.starvision.data

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
    private fun appInstalledOrNot(context: Context, packageName:String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        return false
    }
    fun openApp(context: Context, destinationPackageName:String,activity:String){
        try {
            Log.e("try","$destinationPackageName.$activity")
            val intent = Intent(Intent.ACTION_MAIN)
            intent.component = ComponentName(destinationPackageName, "$destinationPackageName.$activity")
            context.startActivity(intent)
        }catch (e:android.content.ActivityNotFoundException){
            Log.e("catch","1")
            try {
                Log.e("try","2")
                if (appInstalledOrNot(context,"com.android.vending")) {
                    Log.e("try","if")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=$destinationPackageName")
                    intent.setClassName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity")
                    context.startActivity(intent)
                }else{
                    Log.e("try","else")
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$destinationPackageName")))
                }
            }catch (e:android.content.ActivityNotFoundException){
                Log.e("catch","2")
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$destinationPackageName")))
                e.printStackTrace()
            }catch (e:Exception){
                Log.e("catch","3")
                e.printStackTrace()
            }
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
}