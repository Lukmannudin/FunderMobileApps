package com.team.oleg.funder

import android.app.Application
import android.content.res.Configuration
import com.facebook.drawee.backends.pipeline.Fresco

class MyCustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(applicationContext)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}