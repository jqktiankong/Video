package com.jqk.video.view.mine

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jqk.video.R
import com.jqk.video.base.BaseFragment
import com.jqk.video.databinding.FragmentMineBinding
import com.jqk.video.util.Constants
import com.jqk.video.util.DateUtil
import com.jqk.video.util.SPUtils
import com.jqk.video.util.StatusBarUtil
import com.jqk.video.view.about.AboutActivity
import com.jqk.video.view.mianze.MianzeActivity
import com.jqk.video.view.setting.SettingActivity
import com.jqk.video.view.vip.VipActivity

class MineFragment : BaseFragment() {

    var binding: FragmentMineBinding? = null
    var phone: String? = null
    var overDate: String? = null
    var isOver: Int = 1
    var isAc: Int = 0

    var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                Constants.BROADCAST_REFRESH_ACTIVI -> {
                    getData()
                    setData()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_mine, container, false)
        binding = DataBindingUtil.bind(view)
        binding!!.view = this
        StatusBarUtil.setPadding(context, binding!!.contentView)

        getData()
        setData()

        var intentFilter = IntentFilter()
        intentFilter.addAction(Constants.BROADCAST_REFRESH_ACTIVI)
        activity!!.registerReceiver(broadcastReceiver, intentFilter)

        return view
    }

    fun getData() {
        phone = SPUtils.get(context, Constants.KEY_PHONE, "") as String
        overDate = SPUtils.get(context, Constants.KEY_OVERDATE, "") as String
        isOver = SPUtils.get(context, Constants.KEY_ISOVER, 0) as Int
        isAc = SPUtils.get(context, Constants.KEY_ISAC, 0) as Int
    }

    fun setData() {
        if (System.currentTimeMillis() > (DateUtil.dateToStamp(overDate) + 24 * 60 * 60 * 1000)) {
            binding!!.time.text = "已过期"
        } else {
            binding!!.time.text = overDate + "到期"
        }
    }

    fun vip(v: View) {

        if (System.currentTimeMillis() > (DateUtil.dateToStamp(overDate) + 24 * 60 * 60 * 1000)) {
            var intent = Intent()
            intent.setClass(activity, VipActivity::class.java)
            startActivity(intent)
        } else {
            showT("VIP已激活")
        }
    }

    fun mianze(v: View) {
        var intent = Intent()
        intent.setClass(activity, MianzeActivity::class.java)
        startActivity(intent)
    }

    fun guanyu(v: View) {
        var intent = Intent()
        intent.setClass(activity, AboutActivity::class.java)
        startActivity(intent)
    }

    fun setting(v: View) {
        var intent = Intent()
        intent.setClass(activity, SettingActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.unregisterReceiver(broadcastReceiver)
    }
}