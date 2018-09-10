package com.jqk.video.view.setting

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivitySettingBinding
import com.jqk.video.dialog.LogoutDialog
import com.jqk.video.message.LogoutMessage
import com.jqk.video.util.Contants
import com.jqk.video.util.SPUtils
import com.jqk.video.view.modify.ModifyActivity
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseActivity() {

    var binding: ActivitySettingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding!!.view = this
        initToolbar(binding!!.toolbar, true)
        setTitle("设置")
    }

    fun modify(v: View) {
        var intent = Intent()
        intent.setClass(this, ModifyActivity::class.java)
        startActivity(intent)
    }

    fun logout(v: View) {
        var dialog = LogoutDialog()
        dialog.setOnClickListener(object : LogoutDialog.OnClickListener {
            override fun onClick() {
                SPUtils.put(this@SettingActivity, Contants.KEY_LOGIN, false)
                showT("已退出登录")
                var intent = Intent()
                intent.action = Contants.BROADCAST_LOGOUT
                sendBroadcast(intent)
                finish()
            }
        })
        dialog.show(supportFragmentManager, "dialog")
    }
}