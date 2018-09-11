package com.jqk.video

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityMainBinding
import com.jqk.video.util.Constants
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

    var homeFragment: HomeFragment? = null
    var mineFragment: MineFragment? = null

    var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                Constants.BROADCAST_LOGOUT -> {
                    showView(1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding!!.view = this
        StatusBarUtil.immersive(this)

        showView(1)
        checkLogin()

        var intentFilter = IntentFilter()
        intentFilter.addAction(Constants.BROADCAST_LOGOUT)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (homeFragment == null && fragment is HomeFragment)
            homeFragment = fragment
        if (mineFragment == null && fragment is MineFragment)
            mineFragment = fragment
    }

    fun checkLogin() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aLong ->
                    if (!(SPUtils.get(this@MainActivity, Constants.KEY_LOGIN, false) as Boolean)) {
                        jumpLogin()
                    }
                }
    }

    fun showView(type: Int) {
        when (type) {
            1 -> {
                ft = supportFragmentManager.beginTransaction()
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                }
                ft!!.replace(R.id.fragment, homeFragment)
                binding!!.homeImg.setImageResource(R.drawable.icon_home_select)
                binding!!.homeTx.setTextColor(resources.getColor(R.color.endColor))
                binding!!.mineImg.setImageResource(R.drawable.icon_mine)
                binding!!.mineTx.setTextColor(resources.getColor(R.color.text))

            }
            2 -> {
                ft = supportFragmentManager.beginTransaction()
                if (mineFragment == null) {
                    mineFragment = MineFragment()
                }
                ft!!.replace(R.id.fragment, mineFragment)
                binding!!.homeImg.setImageResource(R.drawable.icon_home)
                binding!!.homeTx.setTextColor(resources.getColor(R.color.text))
                binding!!.mineImg.setImageResource(R.drawable.icon_mine_select)
                binding!!.mineTx.setTextColor(resources.getColor(R.color.endColor))
            }
        }
        ft!!.commitAllowingStateLoss()
    }

    fun jumpLogin() {
        var intent = Intent()
        intent.setClass(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun homeClick(v: View) {
        showView(1)
    }

    fun mineClick(v: View) {
        if (!(SPUtils.get(this@MainActivity, Constants.KEY_LOGIN, false) as Boolean)) {
            jumpLogin()
        } else {
            showView(2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}
