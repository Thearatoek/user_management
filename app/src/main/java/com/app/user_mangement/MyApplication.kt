package org.example.user.management.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        println("On MyApplication onCreated")
        super.onCreate()
    }
}