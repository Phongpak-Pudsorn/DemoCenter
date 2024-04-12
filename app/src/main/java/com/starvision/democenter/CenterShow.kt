package com.starvision.democenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.starvision.config.Login
import com.starvision.data.Const
import com.starvision.view.center.MainActivity
import com.starvision.view.center.models.CenterModels
import com.starvision.view.center.sub.*
import com.starvision.view.login.LoginActivity

class CenterShow (private val mContext : FragmentActivity) {
    fun getMainCenter(packageName:String){
        val intent = Intent(mContext, MainActivity::class.java)
        intent.putExtra("package",packageName)
        mContext.startActivity(intent)
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
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.goldprice")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.goldprice") {
                val subGoldToDayPage = SubGoldToDayPage()
                subGoldToDayPage.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.goldprice"
    }

    val setShowFragmentLotto : String
    get(){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.starvision.lottothai")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.starvision.lottothai") {
                val subLottothaiPage = SubLottothaiPage()
                subLottothaiPage.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.starvision.lottothai"
    }

    val setShowFragmentCheckLotto : String
    get(){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.lottery")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.lottery") {
                val subSmileLottoPage = SubSmileLottoPage()
                subSmileLottoPage.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.lottery"
    }

    val setShowFragmentZodiac : String
    get() {
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.zodiac")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.zodiac") {
                val subZodiacFragment = SubZodiacFragment()
                subZodiacFragment.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.smileapp.zodiac"
    }

    val setShowFragmentExchange : String
    get() {
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.starvision.exchangerate")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.starvision.exchangerate") {
                val subExchangeFragment = SubExchangeFragment()
                subExchangeFragment.show(mContext.supportFragmentManager, "")
//            }
        }
        return "com.starvision.exchangerate"
    }

    val setShowFragmentOil : String
    get (){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.oil")
            mContext.startActivity(intent)
        } else {
//            if(Const.KEY_PREFS_FRAGMENT == "com.smileapp.oil") {
                val subOilFragment = SubOilFragment()
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