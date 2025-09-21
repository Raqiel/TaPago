package com.example.tapago.ui.screens.shared.data

import android.app.Application
import com.example.tapago.initKoin
import org.koin.android.ext.koin.androidContext

class AppApplication: Application(){
    override fun onCreate() {
        super.onCreate()
       initKoin {
           androidContext(this@AppApplication)
       }
    }
}


