package com.bpointer.productsmvvm.util;


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View

import android.widget.*

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment




fun Fragment.showToast(s: String) {
    Toast.makeText(activity?.applicationContext, s, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(s: String) {
    Toast.makeText(this.applicationContext, s, Toast.LENGTH_SHORT).show()
}

fun Activity.setStatusBarColor(color:Int){
    var flags = window?.decorView?.systemUiVisibility // get current flag
    if (flags != null) {
        if(isColorDark(color)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window?.decorView?.systemUiVisibility = flags
        }else{
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window?.decorView?.systemUiVisibility = flags
        }
    }
    window?.statusBarColor = color
}

fun Activity.isColorDark(color:Int) : Boolean{
    val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness >= 0.5
}

fun View.show() : View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}



fun View.hide() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}
