package com.linji.cabinetutil.mvp

import android.content.Context
import com.trello.rxlifecycle.LifecycleTransformer

interface IBaseView {
    /**
     * 显示网络错误
     */
    fun showNetError()

    fun showError(msg:String)

    var mContext: Context

//    fun showToast(msg: String?)

    fun showLoading()

    fun hideLoading()

    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
    </T> */
    fun <T> bindToLife(): LifecycleTransformer<T>?
}