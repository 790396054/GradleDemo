package com.didi.gradledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imooc.router.annotations.Destination

@Destination(url = "/home/user", description = "用户主页")
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}