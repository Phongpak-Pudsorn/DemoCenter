package com.starvision.data

import android.content.Intent
import android.os.Build
import android.provider.Settings.Secure
import com.google.gson.Gson
import com.starvision.config.CryptoHandler
import com.starvision.config.Login
import com.starvision.config.MyApplication
import com.starvision.view.login.LoginActivity
import com.starvision.view.login.models.LoginModels
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object ParamUtil {
    val ParamsUid:HashMap<String?,String?>
        get() {
            val hashMaps = HashMap<String?,String?>()
            hashMaps["imei"] = Const.getUUID(MyApplication.appContext)
            hashMaps["model"] = Build.MODEL.replace(" ","_")
            hashMaps["platform"] = "Android "+Build.VERSION.SDK_INT
//            hashMaps["version_center_sdk"] = Build.VERSION.SDK_INT.toString()
//            if(Login.isLogin){
                hashMaps["idx"] = CryptoHandler().encrypt(Login.IDX,Const.AES_KEY,"0000000000000000")
//            }
            hashMaps["ts"] = CryptoHandler().encrypt(SimpleDateFormat("HHmmssddMMyyyy").format(Date()).toString() ,Const.AES_KEY,"0000000000000000")
            return hashMaps
        }
}