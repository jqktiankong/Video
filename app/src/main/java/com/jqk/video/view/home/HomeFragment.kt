package com.jqk.video.view.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jqk.video.R
import com.jqk.video.base.BaseFragment
import com.jqk.video.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment() {
    var binding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = DataBindingUtil.bind(view)
        binding!!.view = this
        initToolbar(binding!!.toolbar, false)
        setTitle(binding!!.title, "首页")

        initView()

        return view
    }

    fun initView() {
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val lp = binding!!.firstitem.layoutParams
        lp.height = width / 3
        binding!!.firstitem.layoutParams = lp
        binding!!.secondItem.layoutParams = lp
    }

    fun aiqiyi(v: View) {
        var intent = Intent()
        intent.setClass(activity, WebViewActivity::class.java)
        intent.putExtra("title", "爱奇艺")
        intent.putExtra("url", "http://www.iqiyi.com")
        startActivity(intent)
    }

    fun tengxun(v: View) {
        var intent = Intent()
        intent.setClass(activity, WebViewActivity::class.java)
        intent.putExtra("title", "腾讯视频")
        intent.putExtra("url", "https://v.qq.com")
        startActivity(intent)
    }

    fun mangguo(v: View) {
        var intent = Intent()
        intent.setClass(activity, WebViewActivity::class.java)
        intent.putExtra("title", "芒果TV")
        intent.putExtra("url", "https://www.mgtv.com")
        startActivity(intent)
    }

    fun youku(v: View) {
        var intent = Intent()
        intent.setClass(activity, WebViewActivity::class.java)
        intent.putExtra("title", "优酷")
        intent.putExtra("url", "https://www.youku.com")
        startActivity(intent)
    }
}