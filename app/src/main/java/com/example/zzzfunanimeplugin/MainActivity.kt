package com.example.zzzfunanimeplugin

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply {
            text = "这是ZzzFun动漫MediaBox插件的主界面"
        })
    }
}