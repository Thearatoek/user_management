package com.app.user_mangement.core.service


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.klakmoum.R
import com.app.user_mangement.activity.MainActivity
import com.app.user_mangement.core.constants.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class FirebaseCloudMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        shp.edit().putString(Preferences.TOKEN, token).apply()
    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }
    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Default values
        var title: String = ""
        var body: String = ""

        // 1️⃣ If FCM sends "notification" payload
        message.notification?.let {
            title = it.title ?: ""
            body = it.body ?: ""
        }

        // 2️⃣ If FCM sends "data" payload (custom key-value)
        if (message.data.isNotEmpty()) {
            title = message.data["title"] ?: title
            body = message.data["body"] ?: body
        }

        val notification = NotificationCompat.Builder(this, Notification.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Random.nextInt(), notification)
    }
}