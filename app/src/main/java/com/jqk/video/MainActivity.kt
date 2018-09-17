package com.jqk.video

import android.Manifest
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.util.AsyncListUtil
import android.view.View
import android.widget.Toast
import com.jqk.video.base.BaseActivity
import com.jqk.video.bean.AppVersion
import com.jqk.video.databinding.ActivityMainBinding
import com.jqk.video.dialog.UpdateDialog
import com.jqk.video.listener.OnDataCallback
import com.jqk.video.util.APKVersionCodeUtils
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
    val INSTALL_PACKAGES_REQUESTCODE = 1

    var binding: ActivityMainBinding? = null
    var ft: FragmentTransaction? = null

    var homeFragment: HomeFragment? = null
    var mineFragment: MineFragment? = null

    var model: MainModel? = null
    var updateUrl: String? = null

    var updatedialog: UpdateDialog? = null

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

        model = MainModel()
    }

    override fun onStart() {
        super.onStart()

        getVersion()
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

    fun getVersion() {
        if ((SPUtils.get(this@MainActivity, Constants.KEY_LOGIN, false) as Boolean)) {
            model!!.getNowAppVersion(object : OnDataCallback<AppVersion> {
                override fun onSuccess(data: AppVersion) {
                    update(data)
                }

                override fun onError() {

                }
            })
        }
    }

    fun update(appVersion: AppVersion) {
        try {
            if (APKVersionCodeUtils.getVersionCode(this) < Integer.parseInt(appVersion.data.ver)) {
                // 开始下载
                updateUrl = appVersion.data.dowmLink
            }
            checkApi()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @TargetApi(26)
    fun checkApi() {
        if (Build.VERSION.SDK_INT >= 26) {
            val b = packageManager.canRequestPackageInstalls()
            if (b) {
                showUpdateDialog(updateUrl!!)
            } else {
                //请求安装未知应用来源的权限
                var packageURI = Uri.parse("package:" + this.getPackageName())
                var intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
                startActivityForResult(intent, INSTALL_PACKAGES_REQUESTCODE)
            }
        } else {
            showUpdateDialog(updateUrl!!)
        }
    }

    fun showUpdateDialog(url: String) {
        if (updatedialog == null) {
            updatedialog = UpdateDialog()
            updatedialog!!.setOnUpdateListener(object : UpdateDialog.OnUpdateListener {
                override fun onStart() {
                    val intent = Intent(this@MainActivity, UpdateService::class.java)
                    intent.putExtra("url", url)
                    startService(intent)
                }
            })

            if (!updatedialog!!.isAdded() && !updatedialog!!.isVisible() && !updatedialog!!.isRemoving()) {
                updatedialog!!.show(supportFragmentManager, "UpdateDialog")
            }

        } else {
            if (!updatedialog!!.isAdded() && !updatedialog!!.isVisible() && !updatedialog!!.isRemoving()) {
                updatedialog!!.show(supportFragmentManager, "UpdateDialog")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == INSTALL_PACKAGES_REQUESTCODE) {
            showUpdateDialog(updateUrl!!)
        } else {
            Toast.makeText(this, "无法下载新版本", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        model!!.onDestroy()
    }
}
