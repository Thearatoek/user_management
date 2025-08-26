package org.example.user.management.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import com.app.user_mangement.core.constants.Notification
import com.google.firebase.messaging.FirebaseMessaging


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        println("On MyApplication onCreated")
        super.onCreate()
        createNotificationChannel()
        subscribeTopic()

    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Notification.NOTIFICATION_CHANNEL_ID,
                Notification.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Just to send notification, you know!"
                enableLights(true)
                lightColor = Color.RED
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(Notification.TOPIC)
    }
}