package com.jqk.video

import com.jqk.video.base.BaseModel
import com.jqk.video.bean.AppVersion
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.retrofit.GetRetrofitService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainModel : BaseModel() {
    private var observable: Observable<AppVersion>? = null

    fun getNowAppVersion(onDataCallback: OnDataCallback<AppVersion>) {
        observable = GetRetrofitService.getRetrofitService().getNowAppVersion()
        observable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<AppVersion> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(appVersion: AppVersion) {
                        onDataCallback.onSuccess(appVersion)
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