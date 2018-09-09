package com.jqk.video

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityMainBinding
import com.jqk.video.util.Contants
import com.jqk.video.util.SPUtils
import com.jqk.video.util.StatusBarUtil
import com.jqk.video.view.home.HomeFragment
import com.jqk.video.view.login.LoginActivity
import com.jqk.video.view.mine.MineFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    var binding: ActivityMainBinding? = null
    var ft: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        StatusBarUtil.immersive(this)

        showView(1)

        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aLong ->
                    if (!(SPUtils.get(this@MainActivity, Contants.KEY_LOGIN, false) as Boolean)) {
                        jumpLogin()
                    }
                }
    }

    fun showView(type: Int) {
        when (type) {
            1 -> {
                ft = supportFragmentManager.beginTransaction()
                ft!!.replace(R.id.fragment, HomeFragment())
                binding!!.homeImg.setImageResource(R.drawable.icon_home_select)
                binding!!.homeTx.setTextColor(resources.getColor(R.color.endColor))
                binding!!.mineImg.setImageResource(R.drawable.icon_mine)
                binding!!.mineTx.setTextColor(resources.getColor(R.color.text))

            }
            2 -> {
                ft = supportFragmentManager.beginTransaction()
                ft!!.replace(R.id.fragment, MineFragment())
                binding!!.homeImg.setImageResource(R.drawable.icon_home)
                binding!!.homeTx.setTextColor(resources.getColor(R.color.text))
                binding!!.mineImg.setImageResource(R.drawable.icon_mine_select)
                binding!!.mineTx.setTextColor(resources.getColor(R.color.endColor))
            }
        }
        ft!!.commit()
    }

    fun jumpLogin() {
        var intent = Intent()
        intent.setClass(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        Log.d("1", "onDestroy")
        super.onDestroy()
    }
}
