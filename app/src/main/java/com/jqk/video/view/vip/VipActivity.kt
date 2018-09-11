package com.jqk.video.view.vip

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.bean.ActiviCode
import com.jqk.video.databinding.ActivityVipBinding
import com.jqk.video.util.Constants
import com.jqk.video.util.SPUtils

class VipActivity : BaseActivity() {

    var binding: ActivityVipBinding? = null
    var viewModel: VipViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vip)
        initToolbar(binding!!.toolbar, true)
        setTitle(binding!!.title, "VIP激活")
        viewModel = VipViewModel(this, binding!!)
    }

    fun getCode(): String {
        return binding!!.code.text.trim().toString()
    }

    fun activiSuccess(data: ActiviCode.DataBean) {
        showT("激活成功")
        SPUtils.put(this, Constants.KEY_OVERDATE, data.overDate)
        SPUtils.put(this, Constants.KEY_ISOVER, data.isOver)
        SPUtils.put(this, Constants.KEY_ISAC, data.isAc)
        var intent = Intent()
        intent.action = Constants.BROADCAST_REFRESH_ACTIVI
        finish()
    }

    fun activiFail(message: String) {
        showT("激活失败" + message)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel!!.onDestroy()
    }
}