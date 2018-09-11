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
import com.jqk.video.util.SPUtils
import com.jqk.video.util.StatusBarUtil
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
        binding!!.phone.text = phone
        if (isOver == 1) {
            binding!!.time.text = "已过期"
        } else {
            binding!!.time.text = overDate
        }
    }

    fun vip(v: View) {

//        if (isAc == 1) {
//            showT("已激活")
//        } else {
//            var intent = Intent()
//            intent.setClass(activity, VipActivity::class.java)
//            startActivity(intent)
//        }

        var intent = Intent()
        intent.setClass(activity, VipActivity::class.java)
        startActivity(intent)

    }

    fun mianze(v: View) {

    }

    fun guanyu(v: View) {

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