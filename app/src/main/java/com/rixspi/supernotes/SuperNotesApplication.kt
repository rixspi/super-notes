package com.rixspi.supernotes

import android.app.Application
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SuperNotesApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}