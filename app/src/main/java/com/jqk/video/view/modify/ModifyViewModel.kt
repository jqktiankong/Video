package com.jqk.video.view.modify

import android.view.View
import com.jqk.video.base.BaseViewModel
import com.jqk.video.bean.Modify
import com.jqk.video.databinding.ActivityModifyBinding
import com.jqk.video.listener.OnDataCallback

class ModifyViewModel(view: ModifyActivity, binding: ActivityModifyBinding) : BaseViewModel() {
    var view: ModifyActivity
    var binding: ActivityModifyBinding
    var model: ModifyModel

    init {
        this.view = view
        this.binding = binding
        model = ModifyModel()
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
        model.modify(phone, password, object : OnDataCallback<Modify> {
            override fun onSuccess(data: Modify) {
                view.hideProgress()
                if (data.code == 200) {
                    view.modifySuccess()
                } else {
                    view.modifyFail(data.message)
                }
            }

            override fun onError() {
                view.hideProgress()
                view.modifyFail("")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}