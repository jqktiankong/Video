package com.jqk.video.base

import io.reactivex.disposables.CompositeDisposable

open class BaseModel {
    var compositeDisposable: CompositeDisposable? = null

    constructor () {
        compositeDisposable = CompositeDisposable()
    }

    open fun onDestroy() {
        compositeDisposable!!.clear()
    }
}
