package com.jqk.video.view.vip

import android.text.TextUtils
import android.view.View
import com.jqk.video.base.BaseViewModel
import com.jqk.video.bean.ActiviCode
import com.jqk.video.databinding.ActivityVipBinding
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.util.Constants
import com.jqk.video.util.SPUtils
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.widget.Toast
import com.jqk.video.util.DateUtil


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
        val overDate = SPUtils.get(view, Constants.KEY_OVERDATE, "") as String
        if (!(System.currentTimeMillis() > (DateUtil.dateToStamp(overDate) + 24 * 60 * 60 * 1000))) {
            view.showT("VIP已激活")
            return
        }

        val phone = SPUtils.get(view, Constants.KEY_PHONE, "") as String
        val password = SPUtils.get(view, Constants.KEY_PASSWORD, "") as String
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

    fun copy(v: View) {
        val text = "539811283"
        val myClipboard: ClipboardManager? = view.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val myClip = ClipData.newPlainText("text", text)
        myClipboard!!.setPrimaryClip(myClip)
        Toast.makeText(view, "群号已复制到粘贴板", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}