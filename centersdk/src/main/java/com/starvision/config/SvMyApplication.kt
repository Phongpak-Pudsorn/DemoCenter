package com.starvision.config

import android.app.Application
import android.content.Context

class SvMyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}