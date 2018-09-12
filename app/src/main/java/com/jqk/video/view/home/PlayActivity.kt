package com.jqk.video.view.home

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.jqk.video.R
import com.jqk.video.base.BaseActivity
import com.jqk.video.databinding.ActivityPlayBinding
import com.jqk.video.util.Constants
import android.view.ViewGroup


class PlayActivity : BaseActivity() {
    var binding: ActivityPlayBinding? = null
    var channel: String = Constants.CHANNEL1
    var url = ""
    var isShow: Boolean = true
    var isShowing: Boolean = false
    var oldList: List<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play)
        binding!!.view = this
        initToolbar(binding!!.toolbar, true)
        setTitle(binding!!.title, "播放")

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

        this.getWindow().getDecorView().addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (oldList != null && getAllChildViews(this.getWindow().getDecorView()).size > oldList!!.size) {

                for (view in getAllChildViews(this.getWindow().getDecorView())) {

                    if (!oldList!!.contains(view)) {

                        view.visibility = View.GONE
                    }
                }
            }

            val outView = ArrayList<View>()
            this.getWindow().getDecorView().findViewsWithText(outView, "缓存", View.FIND_VIEWS_WITH_TEXT)
            this.getWindow().getDecorView().findViewsWithText(outView, "分享", View.FIND_VIEWS_WITH_TEXT)
            val size = outView.size
            if (outView != null && outView.size > 0) {
                oldList = getAllChildViews(this.getWindow().getDecorView())
                outView[0].visibility = View.GONE
            }
        })


        url = intent.getStringExtra("url")
        binding!!.webView.loadUrl(channel + url)

        binding!!.mFrameLayout.setOnDownListener(object : MyFrameLayout.OnDownListener {
            override fun onDown() {
//                anim()
            }
        })
    }

    private fun getAllChildViews(view: View): List<View> {
        val allchildren = ArrayList<View>()
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val viewchild = view.getChildAt(i)
                allchildren.add(viewchild)
                allchildren.addAll(getAllChildViews(viewchild))
            }
        }
        return allchildren
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.play_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_channel1 -> {
                channel = Constants.CHANNEL1
                binding!!.webView.loadUrl(channel + url)
                return true
            }
            R.id.action_channel2 -> {
                channel = Constants.CHANNEL2
                binding!!.webView.loadUrl(channel + url)
                return true
            }
            R.id.action_channel3 -> {
                channel = Constants.CHANNEL3
                binding!!.webView.loadUrl(channel + url)
                return true
            }
            R.id.action_channel4 -> {
                channel = Constants.CHANNEL4
                binding!!.webView.loadUrl(channel + url)
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    fun anim() {
        if (isShow) {
            if (!isShowing) {
                hide()
            }
        } else {
            if (!isShowing) {
                show()
            }
        }
    }

    fun fullScreen() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }
    }

    fun hide() {
        var objectAnimation = ObjectAnimator.ofFloat(binding!!.toolbar, "translationY", 0f, -binding!!.toolbar.measuredHeight.toFloat())
        objectAnimation.duration = 200
        objectAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                isShow = false
                isShowing = false
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
                isShow = true
                isShowing = true
                val decorView = getWindow().getDecorView()
                val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                decorView.setSystemUiVisibility(uiOptions)
            }
        })
        objectAnimation.start()
    }

    fun show() {
        var objectAnimation = ObjectAnimator.ofFloat(binding!!.toolbar, "translationY", -binding!!.toolbar.measuredHeight.toFloat(), 0f)
        objectAnimation.duration = 200
        objectAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                isShow = true
                isShowing = false
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
                isShow = false
                isShowing = true
                val decorView = getWindow().getDecorView()
                val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.setSystemUiVisibility(uiOptions)
            }
        })
        objectAnimation.start()
    }
}