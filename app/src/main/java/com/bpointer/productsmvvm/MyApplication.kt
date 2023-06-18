package com.bpointer.productsmvvm

import android.app.Application
import com.bpointer.productsmvvm.core.AppComponent
import com.bpointer.productsmvvm.core.DaggerAppComponent
import com.bpointer.productsmvvm.core.modules.AppModule


class MyApplication : Application() {



    val myComponent: AppComponent
        get() = mMyComponent

    override fun onCreate() {

        super.onCreate()


        mMyComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()



    }

    companion object {
     lateinit var mMyComponent: AppComponent

    }
}

