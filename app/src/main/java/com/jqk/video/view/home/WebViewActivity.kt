package com.jqk.video.view.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityWebviewBinding
import android.view.KeyEvent
import android.view.WindowManager

class WebViewActivity : BaseActivity() {
    var binding: ActivityWebviewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        initToolbar(binding!!.toolbar, true)
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")
        setTitle(binding!!.title, title)

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

        binding!!.webView.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_play -> {
                var intent = Intent()
                intent.setClass(this, PlayActivity::class.java)
                intent.putExtra("url", binding!!.webView.getUrl())
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() === 0) {
            if (binding!!.webView.canGoBack()) {
                binding!!.webView.goBack()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}