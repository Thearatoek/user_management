package com.app.user_mangement.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.data.model.NotificationModel
import com.app.user_mangement.data.respository.firebse_messaging.FirebaseCloudMessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val firebaseRepository : FirebaseCloudMessagingRepository
): ViewModel(){
    suspend fun onPushNotification(){
        viewModelScope.launch {
            firebaseRepository.postNotification(
                notification = NotificationModel(
                    title = "Hell",
                    body = "From Me",
                    token ="cOCtk0_5RgKHwILB0INWm9:APA91bHNeMbQ_d4ypbsMZgFBT6fZd4f8Vyv6Sdk0EKxMx59Zr9KdF63swwHdam5FwfqLwEm6ST9Wk2lr514E_v_qRWNNJgSZL9U6LfD2b5bJ-jQjG7k6YVY" )
            )
        }
    }
}