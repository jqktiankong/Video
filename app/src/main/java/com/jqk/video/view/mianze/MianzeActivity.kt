package com.jqk.video.view.mianze

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityMianzeBinding

class MianzeActivity : BaseActivity() {
    var binding: ActivityMianzeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mianze)
        initToolbar(binding!!.toolbar, true)
        setTitle(binding!!.title, "免责声明")


    }
}