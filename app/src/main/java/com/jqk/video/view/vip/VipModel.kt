package com.jqk.video.view.vip

import com.jqk.video.base.BaseModel
import com.jqk.video.bean.ActiviCode
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.retrofit.GetRetrofitService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class VipModel : BaseModel() {
    private var observable: Observable<ActiviCode>? = null

    fun activiCode(phone: String, password: String, code: String, onDataCallback: OnDataCallback<ActiviCode>) {
        observable = GetRetrofitService.getRetrofitService().activiCode(phone, password, code)
        observable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ActiviCode> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(activiCode: ActiviCode) {
                        onDataCallback.onSuccess(activiCode)
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