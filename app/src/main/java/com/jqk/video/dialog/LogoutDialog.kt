package com.jqk.video.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class LogoutDialog : DialogFragment() {

    interface OnClickListener {
        fun onClick()
    }

    var onclickListener: OnClickListener? = null

    fun setOnClickListener(onclickListener: OnClickListener) {
        this.onclickListener = onclickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(context!!)
        builder.setTitle("是否退出")
        builder.setPositiveButton("是", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                onclickListener!!.onClick()
            }
        })
        builder.setNegativeButton("否", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        })
        return builder.create()
    }
}