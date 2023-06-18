package com.bpointer.productsmvvm.core

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.Surface
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Fade
import androidx.transition.Slide
import com.bpointer.productsmvvm.dialog.DialogProgress

import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject



open class BaseFragment @Inject constructor() : Fragment() {
    internal var bundle: Bundle? = null
    internal var fragment: Fragment? = null


    internal var dialogOK: Dialog? = null
    private var dialogProgress: DialogProgress? = null
    internal var imm: InputMethodManager? = null

    protected var picturePath = ""
    internal var mgr: InputMethodManager? = null



    internal val permissionsPhoto = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)

    fun replaceFragmentWithoutBack(containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        try {
            if (activity == null) return
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    fun replaceFragmentWithBack(
            containerViewId: Int,
            fragment: Fragment,
            fragmentTag: String,
            backStackStateTag: String
    ) {
        try {
            if (activity == null) return
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }



    fun addFragmentWithBack(
        containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        backStackStateTag: String
    ) {
        try {
            if (activity == null) return
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    fun replaceFragmentWithBackFade(
            containerViewId: Int,
            fragment: Fragment,
            fragmentTag: String,
            backStackStateTag: String
    ) {
        try {
            if (activity == null) return
//            fragment.enterTransition = Slide(Gravity.END)
//            fragment.exitTransition = Slide(Gravity.START)
            fragment.enterTransition = Fade(Fade.IN) // Slide(Gravity.END)
            fragment.exitTransition = Fade(Fade.OUT) // Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    fun log(tag: String, str: String) {
        Log.i(tag, str)
    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }



    fun addFragmentWithoutRemove(containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        try {
            if (activity == null) return
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    fun addFragmentWithFade(containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        try {
            if (activity == null) return
            fragment.enterTransition = Fade(Fade.IN) // Slide(Gravity.END)
            fragment.exitTransition = Fade(Fade.OUT) // Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    fun closeKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        }
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getNavigationBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        return result
    }





    fun getCurrentFragment(): Fragment {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTag =
            fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
        return fragmentManager.findFragmentByTag(fragmentTag)!!
    }
    fun clearBackStack() {
        try {
            val manager = requireActivity().supportFragmentManager
            val first = manager.getBackStackEntryAt(0)
            Log.i(javaClass.name, "$first")
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearBackStack(tagName: String) {
        try {
            val manager = requireActivity().supportFragmentManager
            for (i in 0 until manager.backStackEntryCount) {
                val first = manager.getBackStackEntryAt(i)
                if (first.name == tagName) {
                    manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }





    fun showProgressDialog() {
        if (activity != null) {
            if (dialogProgress != null && dialogProgress!!.isShowing)
                dialogProgress!!.dismiss()
            dialogProgress = DialogProgress(requireActivity())
            dialogProgress!!.show()
        }
    }

    fun hideProgressDialog() {
        if (dialogProgress != null && dialogProgress!!.isShowing)
            dialogProgress!!.dismiss()
    }



    fun getNavigationBarPadding(activity: Activity): Int {
        val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val appUsableSize = Point()
        display.getSize(appUsableSize)
        val realScreenSize = Point()
        display.getRealSize(realScreenSize)
        var navigationSize = Point()
        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            navigationSize = Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
        }
        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            navigationSize = Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        }
        var navigationBarHeight = 0
        val resourceId = resources.getIdentifier(
            "navigation_bar_height",
            "dimen", "android"
        )
        // Check navigation bar existence
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        // Validate navigation bar uses in our app
        val padding =
            (if (navigationSize.y > navigationSize.x) navigationSize.x
            else navigationSize.y) - navigationBarHeight
        val rotation = windowManager.defaultDisplay.rotation
        val isPortrait = rotation == Surface.ROTATION_0
        if (padding > 0) return padding
        return 0
    }



}
