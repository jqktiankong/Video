package com.jqk.video.view.register

import android.util.Log
import android.view.View
import com.jqk.video.base.BaseViewModel
import com.jqk.video.bean.Register
import com.jqk.video.databinding.ActivityRegisterBinding
import com.jqk.video.listener.OnDataCallback

class RegisterViewModel(view: RegisterActivity, binding: ActivityRegisterBinding) : BaseViewModel() {
    var view: RegisterActivity
    var binding: ActivityRegisterBinding
    var model: RegisterModel

    init {
        this.view = view
        this.binding = binding
        model = RegisterModel()
        this.binding.viewModel = this
    }

    fun getCode(v: View) {
        view.getCode()
    }

    fun verificationCode(v: View) {
        view.verificationCode()
    }

    fun register() {
        val phone = binding!!.phone.text.trim().toString()
        val password = binding!!.password.text.trim().toString()

        view.showProgress()
        model.register(phone, password, object : OnDataCallback<Register> {
            override fun onSuccess(data: Register) {
                view.hideProgress()
                if (data.code == 200) {
                    view.registerSuccess()
                } else {
                    view.registerFail(data.message)
                }
            }

            override fun onError() {
                view.hideProgress()
                view.registerFail("")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}