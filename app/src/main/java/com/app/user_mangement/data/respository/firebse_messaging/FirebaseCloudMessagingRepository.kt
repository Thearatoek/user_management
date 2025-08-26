package com.app.user_mangement.data.respository.firebse_messaging

import com.app.user_mangement.data.model.NotificationModel
import okhttp3.ResponseBody
import retrofit2.Response

interface FirebaseCloudMessagingRepository{
    suspend fun postNotification(notification: NotificationModel): Response<ResponseBody>
}