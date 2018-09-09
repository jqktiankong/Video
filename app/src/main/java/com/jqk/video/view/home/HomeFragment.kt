package com.jqk.video.view.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
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
        initToolbar(binding!!.toolbar, false)
        setTitle(binding!!.title, "首页")
        return view
    }
}