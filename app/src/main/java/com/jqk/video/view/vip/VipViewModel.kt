package com.jqk.video.view.vip

import android.text.TextUtils
import android.view.View
import com.jqk.video.base.BaseViewModel
import com.jqk.video.bean.ActiviCode
import com.jqk.video.databinding.ActivityVipBinding
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.util.Contants
import com.jqk.video.util.SPUtils

class VipViewModel(view: VipActivity, binding: ActivityVipBinding) : BaseViewModel() {
    var view: VipActivity
    var binding: ActivityVipBinding
    var model: VipModel

    init {
        this.view = view
        this.binding = binding
        this.binding.viewModel = this
        model = VipModel()
    }

    fun activiCode(v: View) {
        val phone = SPUtils.get(view, Contants.KEY_PHONE, "") as String
        val password = SPUtils.get(view, Contants.KEY_PASSWORD, "") as String
        val code = view.getCode()

        if (TextUtils.isEmpty(code)) {
            view.showT("激活码为空")
            return
        }

        view.showProgress()
        model.activiCode(phone, password, code, object : OnDataCallback<ActiviCode> {
            override fun onSuccess(data: ActiviCode) {
                view.hideProgress()
                if (data.code == 200) {
                    view.activiSuccess(data.data)
                } else {
                    view.activiFail(data.message)
                }
            }

            override fun onError() {
                view.hideProgress()
                view.activiFail("")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}