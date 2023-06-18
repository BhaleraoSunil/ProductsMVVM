package com.bpointer.productsmvvm.util

import android.view.View

class OnClickListener(private var position: Int, onClickCallback: OnClickCallback, var type: String = "") : View.OnClickListener {
    private var onClickCallback: OnClickCallback? = onClickCallback

    override fun onClick(view: View) {
        onClickCallback?.onClicked(view, position,type)
    }

    interface OnClickCallback {
        fun onClicked(view: View, position: Int, type: String)
    }
}
