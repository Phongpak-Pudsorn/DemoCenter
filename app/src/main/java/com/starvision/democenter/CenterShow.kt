package com.starvision.democenter

import android.content.Context
import android.content.Intent
import com.starvision.view.center.MainActivity
import com.starvision.view.center.models.CenterModels

class CenterShow (private val mContext : Context) {
    fun getMainCenter(){
        val intent = Intent(mContext, MainActivity::class.java)
        mContext.startActivity(intent)
    }

    fun setShowFragmentLotto() {
        val intent = Intent(mContext, MainActivity::class.java)
        intent.putExtra("fragment",selectorFragment("1"))
        mContext.startActivity(intent)
    }

    val setShowFragmentGold : String
        get() {
            val intent = Intent(mContext, MainActivity::class.java)
            intent.putExtra("fragment",mContext.getString(com.starvision.luckygamesdk.R.string.gold_package))
            mContext.startActivity(intent)
            return ""
        }


    private fun selectorFragment(fragment : String) : String {
        var arr = ""
        for (i in MainActivity().packageName.indices){
            if(MainActivity().packageName[i].bannerId == fragment){
                arr = MainActivity().packageName[i].bannerLinkstoreapp
            }
        }
        return arr
    }

}