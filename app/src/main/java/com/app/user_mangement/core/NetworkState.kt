package com.app.user_mangement.core

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
    object Lost : NetworkStatus()
}