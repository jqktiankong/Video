package com.jqk.video.base

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import com.jqk.video.dialog.ProgressDialog
import com.jqk.video.util.StatusBarUtil

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var ft: FragmentTransaction? = null


    fun initToolbar(toolbar: Toolbar, back: Boolean) {
        StatusBarUtil.immersive(this)
        StatusBarUtil.setHeightAndPadding(this, toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        if (back) {
            var ab = getSupportActionBar()
            ab!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun setTitle(view: TextView, title: String) {
        view.setText(title)
    }

    fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog()
            ft = supportFragmentManager.beginTransaction()
            ft!!.add(progressDialog, "ProgressDialog")
            if (!progressDialog!!.isAdded() && !progressDialog!!.isVisible() && !progressDialog!!.isRemoving()) {
                ft!!.commitAllowingStateLoss()
            }
        } else {
            if (!progressDialog!!.isAdded() && !progressDialog!!.isVisible() && !progressDialog!!.isRemoving()) {
                ft = supportFragmentManager.beginTransaction()
                ft!!.add(progressDialog, "ProgressDialog")
                ft!!.commitAllowingStateLoss()
            }
        }
    }

    fun hideProgress() {
        if (progressDialog != null) {
            progressDialog!!.dismissAllowingStateLoss()
        }
    }

    fun showT(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showT(id: Int) {
        Toast.makeText(this, resources.getText(id), Toast.LENGTH_SHORT).show()
    }
}
