package com.jqk.video.view.register

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityRegisterBinding
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.jqk.video.util.Validator
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class RegisterActivity : BaseActivity() {

    var binding: ActivityRegisterBinding? = null
    var viewModel: RegisterViewModel? = null

    var compositeDisposable: CompositeDisposable? = null

    var eventHandler: EventHandler = object : EventHandler() {
        override fun afterEvent(event: Int, result: Int, data: Any) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            val msg = Message()
            msg.arg1 = event
            msg.arg2 = result
            msg.obj = data
            Handler(Looper.getMainLooper(), object : Handler.Callback {
                override fun handleMessage(msg: Message): Boolean {
                    val event = msg.arg1
                    val result = msg.arg2
                    val data = msg.obj

                    hideProgress()

                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            showT("获取验证码成功，请注意查收短信")
                            countdown()
                        } else {
                            // TODO 处理错误的结果
                            showT("获取验证码失败" + result)
                            (data as Throwable).printStackTrace()
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            showT("验证成功")
                            viewModel!!.register()
                        } else {
                            // TODO 处理错误的结果
                            showT("验证失败" + result)
                            (data as Throwable).printStackTrace()
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false
                }
            }).sendMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = RegisterViewModel(this, binding!!)
        initToolbar(binding!!.toolbar, true)
        setTitle(binding!!.title, "注册")

        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler)

        compositeDisposable = CompositeDisposable()
    }

    fun getCode() {
        val phone = binding!!.phone.text.trim().toString()
        if (TextUtils.isEmpty(phone)) {
            showT("手机号为空")
            return
        }

        if (!Validator.isMobile(phone)) {
            showT("手机号格式不正确")
            return
        }

        showProgress()
        SMSSDK.getVerificationCode("86", phone)
    }

    fun verificationCode() {
        val phone = binding!!.phone.text.trim().toString()
        val code = binding!!.code.text.trim().toString()
        val password = binding!!.password.text.trim().toString()

        if (TextUtils.isEmpty(phone)) {
            showT("手机号为空")
            return
        }

        if (!Validator.isMobile(phone)) {
            showT("手机号格式不正确")
            return
        }

        if (TextUtils.isEmpty(code)) {
            showT("验证码为空")
            return
        }

        if (TextUtils.isEmpty(password)) {
            showT("密码为空")
            return
        }

        SMSSDK.submitVerificationCode("86", phone, code);
    }

    fun countdown() {
        Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(t: Long) {
                        binding!!.getCode.text = ((60 - t).toInt()).toString()
                        if (t == 60L) {
                            compositeDisposable!!.clear()
                            binding!!.getCode.text = "获取验证码"
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    fun registerSuccess() {
        showT("注册成功")
        finish()
    }

    fun registerFail(message: String) {
        showT("注册失败" + message)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel!!.onDestroy()
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}