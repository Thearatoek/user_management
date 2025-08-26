package com.app.user_mangement.data.respository.firebse_messaging

import com.app.user_mangement.data.datasource.remote.ApiInterface
import com.app.user_mangement.data.model.NotificationModel
import com.app.user_mangement.di.ApiService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class FirebaseCloudMessagingRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : FirebaseCloudMessagingRepository{
    override suspend fun postNotification(notification: NotificationModel): Response<ResponseBody> {
        return  api.postNotification(notification = notification)
    }
}