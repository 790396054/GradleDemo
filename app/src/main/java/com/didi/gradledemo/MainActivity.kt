package com.didi.gradledemo

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.imooc.router.annotations.Destination

@Destination(url = "/home/page", description = "应用主页")
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mthod1()
        hello()
        Log.d(TAG, "channelName : ${getChannelName()}")
    }

    fun mthod1() {

    }

    private fun hello() {

    }

    private fun getChannelName() : String {
        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        return appInfo.metaData.getString("MTA_CHANNEL") ?: ""
    }
}