package com.jqk.video.view.home

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class MyWebViewX5 : FrameLayout {

    private var webView: X5WebView? = null
    private var progressBar: ProgressBar? = null
    private var density: Float = 0.toFloat()
    private var activity: Activity? = null
    private var firstUrl = ""
    private var currentUrl = ""
    private var first = true

    constructor(p0: Context?) : super(p0) {
        init()
    }

    constructor(p0: Context?, p1: AttributeSet?) : super(p0, p1) {
        init()
    }

    constructor(p0: Context?, p1: AttributeSet?, p2: Int) : super(p0, p1, p2) {
        init()
    }

    fun init() {
        var dm = DisplayMetrics()
        dm = resources.displayMetrics

        density = dm.density

        webView = X5WebView(context)
        val webViewLp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(webView, webViewLp)

        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        val progressBarLp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (3 * density).toInt())
        progressBar!!.setMax(100)
        addView(progressBar, progressBarLp)

        webView!!.setWebViewClient(object : WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })
        webView!!.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {

                if (newProgress == 100) {
                    progressBar!!.setVisibility(View.GONE)//加载完网页进度条消失
                } else {
                    progressBar!!.setVisibility(View.VISIBLE)//开始加载网页时显示进度条
                    progressBar!!.setProgress(newProgress)//设置进度值
                }
            }
        })

        webView!!.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                if (first) {
                    firstUrl = getUrl()
                    first = false
                }
                currentUrl = getUrl()
            }
        })

        webView!!.setLayerType(0, null)
        webView!!.setDrawingCacheEnabled(true)
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    fun loadUrl(url: String) {
        firstUrl = url
        webView!!.loadUrl(url)
    }

    fun getUrl(): String {
        return webView!!.getUrl()
    }

    fun canGoBack(): Boolean {

        return if (webView!!.canGoBack() && firstUrl != currentUrl) {
            true
        } else {
            false
        }
    }

    fun goBack() {
        webView!!.goBack()
    }
}