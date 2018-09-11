package com.jqk.video.view.login

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.jqk.video.base.BaseActivity
import com.jqk.video.R
import com.jqk.video.bean.Login
import com.jqk.video.databinding.ActivityLoginBinding
import com.jqk.video.util.Constants
import com.jqk.video.util.SPUtils
import com.jqk.video.view.modify.ModifyActivity
import com.jqk.video.view.register.RegisterActivity

class LoginActivity : BaseActivity() {

    private var binding: ActivityLoginBinding? = null
    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = LoginViewModel(this, binding!!)
        initToolbar(binding!!.toolbar, true)
        setTitle(binding!!.title, "登录")
    }

    fun getPhone(): String {
        return binding!!.phone.text.trim().toString()
    }

    fun getPassword(): String {
        return binding!!.password.text.trim().toString()
    }

    fun loginSuccess(data: Login.DataBean) {
        SPUtils.put(this, Constants.KEY_LOGIN, true)
        SPUtils.put(this, Constants.KEY_PHONE, data.phone)
        SPUtils.put(this, Constants.KEY_PASSWORD, getPassword())
        SPUtils.put(this, Constants.KEY_OVERDATE, data.overDate)
        SPUtils.put(this, Constants.KEY_ISOVER, data.isOver)
        SPUtils.put(this, Constants.KEY_ISAC, data.isAc)
        finish()
    }

    fun loginError() {
        showT("登录失败")
    }

    fun jumpRegister() {
        var intent = Intent()
        intent.setClass(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun jumpModify() {
        var intent = Intent()
        intent.setClass(this, ModifyActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel!!.onDestroy()
    }
}
