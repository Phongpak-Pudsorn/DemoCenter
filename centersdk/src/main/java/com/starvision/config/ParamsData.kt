package com.starvision.config

import com.starvision.api.Api
import com.starvision.api.ApiClient
import com.starvision.api.URL
import com.starvision.data.Const
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParamsData(private var mPostLoadListener : PostLoadListener) {
    private val TAG = javaClass.simpleName

    interface PostLoadListener{
        fun onSuccess(body : String)
        fun onFailed(t: Throwable)
    }

    fun postLoadData(urlBase : String,path :String,port : String ,hashMap : HashMap<String?,String?>){
        val apiService = ApiClient().getBaseLink(urlBase,port).create(Api::class.java)
        apiService.postRequest(path,hashMap).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    mPostLoadListener.onSuccess(response.body()!!.string())
                }catch (e : Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    mPostLoadListener.onFailed(t)
                }catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun getLoadData(urlBase : String,path :String,port: String){
        val apiService = ApiClient().getBaseLink(urlBase, port).create(Api::class.java)
        apiService.getRequest(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    mPostLoadListener.onSuccess(response.body()!!.string())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    mPostLoadListener.onFailed(t)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }


//    lateinit var callGetContact : CallGetContact
//    @SuppressLint("StaticFieldLeak")
//    inner class CallGetContact(private val strPacket : String, private val bGetFriendContact : Boolean) : ViewModel()  {
//        private var bRunning = true
//        private var strEmi = ConstVisionInstallSDK.getUUID(mContext)!!
//        private var arrayContact = ContactModel()
//        private val dataMachine = ContactModel.DataDetailRow()
//        fun execute() = viewModelScope.launch {
//            val result = doInBackground() // runs in background thread without blocking the Main Thread
//            onPostExecute(result)
//        }
//
//        @SuppressLint("HardwareIds")
//        private suspend fun doInBackground() : String = withContext(Dispatchers.IO) {
//            while (bRunning){
//                if (execute().isCancelled){
//                    break
//                } else {
//                    try {
//                        dataMachine.Brand = Build.BRAND
//                        dataMachine.OS = "Android"
//                        dataMachine.Device_Name = Settings.Secure.getString(mContext.contentResolver, Settings.Secure.ANDROID_ID)
//                        dataMachine.Model = Build.MODEL
//                        dataMachine.IMEI_UDID = strEmi
//                        dataMachine.System_Version = ConstVisionInstallSDK.getAPIVersion()
//                        dataMachine.Platform = ConstVisionInstallSDK.getAPIVersion()
//
//                        arrayContact = ContactModel(strEmi,strPacket,ConstVisionInstallSDK.strAppBannerID,"Thailand"
//                            ,"R71D1A6E23A8DB372E9E9D33E3CB4AB38D0A5",dataMachine)
//
////                        Const.log(tagS, "CallGetContact : ")
////                        Const.log(tagS, "ContactModel : "+arrayContact.machineIMEI
////                                +" "+arrayContact.appAdsPackage
////                                +" "+arrayContact.appBannerId
////                                +" "+arrayContact.appBannerCountry
////                                +" "+arrayContact.checked)
////                        Const.log(tagS, "dataMachine : "+dataMachine.OS
////                                +" "+dataMachine.Brand
////                                +" "+dataMachine.Model
////                                +" "+dataMachine.Device_Name
////                                +" "+dataMachine.IMEI_UDID
////                                +" "+dataMachine.Platform
////                                +" "+dataMachine.System_Version)
//                        bRunning = false
//                    }catch (e : Exception){
////                        Const.log(tagS, "catch :")
//                        bRunning = false
//                    }
//                }
//            }
//            return@withContext ""
//        }
//        private fun onPostExecute(result: String){
//            try {
////                Const.log(tagS,"result : $result")
//                if(!bGetFriendContact){
//                    callWebServerInstall = CallWebServerInstall(ConstVisionInstallSDK.getGoogleUrl(strEmi), false)
//                    callWebServerInstall.execute()
//                } else {
//                    callWebServerInstall = CallWebServerInstall(ConstVisionInstallSDK.getGoogleUrl(strEmi), false)
//                    callWebServerInstall.execute()
//                }
//            }catch (e : Exception){
////                Const.log(tagS, "catch :")
//                e.printStackTrace()
//            }
//        }
//
//    }

}