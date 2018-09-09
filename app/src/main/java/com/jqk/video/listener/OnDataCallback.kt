package com.jqk.video.listener

interface OnDataCallback<T> {
    fun onSuccess(data: T)
    fun onError()
}