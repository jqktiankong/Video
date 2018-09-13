package com.jqk.video.app

import android.app.Application

import com.jqk.video.retrofit.GetRetrofitService
import com.mob.MobApplication
import com.mob.MobSDK

class MyApplication : MobApplication() {
    override fun onCreate() {
        super.onCreate()

        GetRetrofitService.init()

        MobSDK.init(this)
    }
}
