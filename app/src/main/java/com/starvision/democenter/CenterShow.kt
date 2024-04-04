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
    fun getMainCenter(){
        val intent = Intent(mContext, MainActivity::class.java)
        mContext.startActivity(intent)
    }

    val setShowFragmentGold : String
    get(){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.goldprice")
            mContext.startActivity(intent)
        } else {
            val subGoldToDayPage = SubGoldToDayPage()
            subGoldToDayPage.show(mContext.supportFragmentManager, "")
        }
        return ""
    }

    val setShowFragmentLotto : String
    get(){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.starvision.lottothai")
            mContext.startActivity(intent)
        } else {
            val subLottothaiPage = SubLottothaiPage()
            subLottothaiPage.show(mContext.supportFragmentManager, "")
        }
        return ""
    }

    val setShowFragmentCheckLotto : String
    get(){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.lottery")
            mContext.startActivity(intent)
        } else {
            val subSmileLottoPage = SubSmileLottoPage()
            subSmileLottoPage.show(mContext.supportFragmentManager, "")
        }
        return ""
    }

    val setShowFragmentZodiac : String
    get() {
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.zodiac")
            mContext.startActivity(intent)
        } else {
            val subZodiacFragment = SubZodiacFragment()
            subZodiacFragment.show(mContext.supportFragmentManager, "")
        }
        return ""
    }

    val setShowFragmentExchange : String
    get() {
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.starvision.exchangerate")
            mContext.startActivity(intent)
        } else {
            val subExchangeFragment = SubExchangeFragment()
            subExchangeFragment.show(mContext.supportFragmentManager, "")
        }
        return ""
    }

    val setShowFragmentOil : String
    get (){
        if(!Login.isLogin){
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.putExtra("fragment","com.smileapp.oil")
            mContext.startActivity(intent)
        } else {
            val subOilFragment = SubOilFragment()
            subOilFragment.show(mContext.supportFragmentManager, "")
        }
        return ""
    }

//    private fun selectorFragment(fragment : String) : String {
//        var arr = ""
//        for (i in MainActivity().packageName.indices){
//            if(MainActivity().packageName[i].bannerId == fragment){
//                arr = MainActivity().packageName[i].bannerLinkstoreapp
//            }
//        }
//        return arr
//    }

}