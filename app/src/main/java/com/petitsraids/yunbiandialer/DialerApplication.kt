package com.petitsraids.yunbiandialer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.petitsraids.yunbiandialer.support.MyUtils

class DialerApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mContext: Context? = null
        var FONT_SIZE: Int = MyUtils.FONT_SIZE_MEDIUM
        var NEW_FONT_SIZE = 0
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        val shared = getSharedPreferences(MyUtils.SHARED_NAME, Context.MODE_PRIVATE)
        FONT_SIZE = shared.getInt(MyUtils.SHARED_KEY, MyUtils.FONT_SIZE_MEDIUM)
        NEW_FONT_SIZE = FONT_SIZE
    }
}