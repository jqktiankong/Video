package com.jqk.video.view.login

import android.text.TextUtils
import android.view.View
import com.jqk.video.base.BaseViewModel
import com.jqk.video.bean.Login
import com.jqk.video.databinding.ActivityLoginBinding
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.util.Validator

class LoginViewModel(view: LoginActivity, binding: ActivityLoginBinding) : BaseViewModel() {
    private var view: LoginActivity
    private var binding: ActivityLoginBinding
    private var model: LoginModel

    init {
        this.view = view
        this.binding = binding
        binding.viewModel = this
        model = LoginModel()
    }

    fun login(v: View) {
        val phone = view.getPhone()
        val password = view.getPassword()

        if (TextUtils.isEmpty(phone)) {
            view.showT("手机号为空")
            return
        }

        if (!Validator.isMobile(phone)) {
            view.showT("手机号格式不正确")
            return
        }

        if (TextUtils.isEmpty(password)) {
            view.showT("密码为空")
            return
        }

        view.showProgress()
        model.login(phone, password, object : OnDataCallback<Login> {
            override fun onSuccess(data: Login) {
                view.hideProgress()
                if (data.code == 200) {
                    view.loginSuccess(data.data.phone)
                } else {
                    view.loginError()
                }
            }

            override fun onError() {
                view.hideProgress()
                view.loginError()
            }
        })
    }

    fun register(v: View) {
        view.jumpRegister()
    }

    fun forget(v: View) {
        view.jumpModify()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}