package com.starvision.democenter

import android.app.Activity
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class CenterBannerShow {
    private val TAG = javaClass.simpleName

    fun getShowBannerAdMob(activity: Activity) {
        log("bannerAds", "getShowBannerAdMob ")
        val adRequest = AdRequest.Builder().build()
        val adView = activity.findViewById<AdView>(R.id.adView)
        try {
            adView.visibility = View.VISIBLE
            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    log(TAG, "ad is loaded bannerAds.")
                    adView.visibility = View.VISIBLE
                }

                override fun onAdOpened() {
                    log("adView", "onAdOpened")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    adView.visibility = View.GONE
                    log("adView", "FailedToLoad:$loadAdError")
                }

                override fun onAdClosed() {
                    log("adView", "onAdClosed")
                }

                fun onAdLeftApplication() {}
            }
            adView.loadAd(adRequest)
        } catch (e: Exception) {
            log("adView", "catch:")
            adView.visibility = View.GONE
        }
    }

    private fun log(strClass :String,strObj: String){
        Log.e(strClass,strObj)
    }
}