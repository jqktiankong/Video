package com.jqk.video.view.login

import com.jqk.video.base.BaseModel
import com.jqk.video.bean.Login
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.retrofit.GetRetrofitService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginModel : BaseModel() {
    private var observable: Observable<Login>? = null

    fun login(phone: String, password: String, onDataCallback: OnDataCallback<Login>) {
        observable = GetRetrofitService.getRetrofitService().login(phone, password)
        observable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Login> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(login: Login) {
                        onDataCallback.onSuccess(login)
                    }

                    override fun onError(e: Throwable) {
                        onDataCallback.onError()
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()

        if (observable != null) {
            observable!!.unsubscribeOn(Schedulers.io())
        }
    }
}