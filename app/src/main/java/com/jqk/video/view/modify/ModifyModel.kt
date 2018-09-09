package com.jqk.video.view.modify

import com.jqk.video.base.BaseModel
import com.jqk.video.bean.Modify
import com.jqk.video.bean.Register
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.retrofit.GetRetrofitService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ModifyModel : BaseModel() {
    private var observable: Observable<Modify>? = null

    fun modify(phone: String, password: String, onDataCallback: OnDataCallback<Modify>) {
        observable = GetRetrofitService.getRetrofitService().modify(phone, password)
        observable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Modify> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(modify: Modify) {
                        onDataCallback.onSuccess(modify)
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