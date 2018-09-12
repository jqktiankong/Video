package com.jqk.video.view.about

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityAboutBinding
import com.jqk.video.util.APKVersionCodeUtils

class AboutActivity : BaseActivity() {
    var binding: ActivityAboutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        initToolbar(binding!!.toolbar, true)
        setTitle(binding!!.title, "关于我们")

        binding!!.versionCode.text = "优看视频v:" + APKVersionCodeUtils.getVerName(this)
    }
}