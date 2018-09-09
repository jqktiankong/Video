package com.jqk.video.view.register

import com.jqk.video.base.BaseModel
import com.jqk.video.bean.Register
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.retrofit.GetRetrofitService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterModel : BaseModel() {
    private var observable: Observable<Register>? = null

    fun register(phone: String, password: String, onDataCallback: OnDataCallback<Register>) {
        observable = GetRetrofitService.getRetrofitService().register(phone, password)
        observable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Register> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(register: Register) {
                        onDataCallback.onSuccess(register)
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