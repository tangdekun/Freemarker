package com.hp.freemarkerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webview.post({
            val templateDirRoot = filesDir.absolutePath
            FreeWarkerUtil.getInstance(this).prepareTemplate("", "")
            val url = "file://" + FreeWarkerUtil.getInstance(this).getHtml("main", "main", null)
            webview.loadUrl(url)
        })
    }
}
