package com.starvision.data

import android.os.Build
import com.starvision.centersdk.BuildConfig
import com.starvision.config.SvCryptoHandler
import com.starvision.config.SvLogin
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object SvParamUtil {
    val ParamsUid:HashMap<String?,String?>
        get() {
            val hashMaps = HashMap<String?,String?>()
            hashMaps["imei"] = SvConst.getUUID(SvConst.appContext)
            hashMaps["model"] = Build.MODEL.replace(" ","_")
            hashMaps["platform"] = "Android "+Build.VERSION.SDK_INT
            hashMaps["version_center_sdk"] = BuildConfig.VERSION_NAME
//            if(Login.isLogin){
                hashMaps["idx"] = SvCryptoHandler()
                    .encrypt(SvLogin.IDX,SvConst.AES_KEY,"0000000000000000")
//            }
            hashMaps["ts"] = SvCryptoHandler()
                .encrypt(SimpleDateFormat("HHmmssddMMyyyy").format(Date()).toString() ,SvConst.AES_KEY,"0000000000000000")
            return hashMaps
        }
}