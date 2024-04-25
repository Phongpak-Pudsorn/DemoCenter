package com.starvision.democenter

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.starvision.config.SvLogin
import com.starvision.data.SvConst
import com.starvision.view.center.SvMainActivity
import com.starvision.view.center.sub.*
import com.starvision.view.login.SvLoginActivity

class CenterShow (private val mContext : FragmentActivity) {
    fun getMainCenter(packageName:String){
        val intent = Intent(mContext, SvMainActivity::class.java)
        intent.putExtra("package",packageName)
        mContext.startActivity(intent)
    }
    fun checkVer(packageName: String):Boolean{
        var app = getApp(packageName)
        Log.e("app",app)
        return SvConst.checkStatusApp(app)
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

//    fun checkFragment (fragment: String){
//        when (fragment) {
//            setShowFragmentGold -> {
//                setShowFragmentGold
//                Const.KEY_PREFS_FRAGMENT = ""
//            }
//            setShowFragmentLotto -> {
//                setShowFragmentLotto
//                Const.KEY_PREFS_FRAGMENT = ""
//            }
//            setShowFragmentCheckLotto -> {
//                setShowFragmentCheckLotto
//                Const.KEY_PREFS_FRAGMENT = ""
//            }
//            setShowFragmentZodiac -> {
//                setShowFragmentZodiac
//                Const.KEY_PREFS_FRAGMENT = ""
//            }
//            setShowFragmentExchange -> {
//                setShowFragmentExchange
//                Const.KEY_PREFS_FRAGMENT = ""
//            }
//            setShowFragmentOil -> {
//                setShowFragmentOil
//                Const.KEY_PREFS_FRAGMENT = ""
//            }
//        }
//    }

    val setShowFragmentGold : String
    get(){
        if(!SvLogin.isLogin){
            val intent = Intent(mContext, SvLoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.goldprice")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.goldprice") {
                val subGoldToDayPage = SvSubGoldToDayFragment()
                subGoldToDayPage.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.goldprice"
    }

    val setShowFragmentLotto : String
    get(){
        if(!SvLogin.isLogin){
            val intent = Intent(mContext, SvLoginActivity::class.java)
            intent.putExtra("fragment","com.starvision.lottothai")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.starvision.lottothai") {
                val subLottothaiPage = SvSubLottothaiFragment()
                subLottothaiPage.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.starvision.lottothai"
    }

    val setShowFragmentCheckLotto : String
    get(){
        if(!SvLogin.isLogin){
            val intent = Intent(mContext, SvLoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.lottery")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.lottery") {
                val subSmileLottoPage = SvSubSmileLottoFragment()
                subSmileLottoPage.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.lottery"
    }

    val setShowFragmentZodiac : String
    get() {
        if(!SvLogin.isLogin){
            val intent = Intent(mContext, SvLoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.zodiac")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.zodiac") {
                val subZodiacFragment = SvSubZodiacFragment()
                subZodiacFragment.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.zodiac"
    }

    val setShowFragmentExchange : String
    get() {
        if(!SvLogin.isLogin){
            val intent = Intent(mContext, SvLoginActivity::class.java)
            intent.putExtra("fragment","com.starvision.exchangerate")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.starvision.exchangerate") {
                val subExchangeFragment = SvSubExchangeFragment()
                subExchangeFragment.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.starvision.exchangerate"
    }

    val setShowFragmentOil : String
    get (){
        if(!SvLogin.isLogin){
            val intent = Intent(mContext, SvLoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.oil")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.oil") {
                val subOilFragment = SvSubOilFragment()
                subOilFragment.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.oil"
    }

//    fun selectorFragment(fragment : String) : String {
//        var arr: ArrayList<CenterModels.CenterData.PageData.IconData>
//        var result = ""
//        for (i in MainActivity().packageName.indices){
//            if(MainActivity().packageName[i].iconappLinkstoregoogle == fragment){
//                arr = MainActivity().packageName
//                result = arr[i].iconappLinkstoregoogle
//            }
//        }
//        return result
//    }

}