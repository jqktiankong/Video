package com.jqk.video

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jqk.video.util.StatusBarUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class WelcomeActivity : AppCompatActivity() {

    var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        StatusBarUtil.immersive(this)

        compositeDisposable = CompositeDisposable()

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable!!.add(d)
                    }

                    override fun onNext(t: Long) {
                        jumpMain()
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    fun jumpMain() {
        var intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable!!.clear()
    }
}