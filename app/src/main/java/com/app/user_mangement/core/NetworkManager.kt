package com.app.user_mangement.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _networkStatus = MutableStateFlow<NetworkStatus>(NetworkStatus.Unavailable)
    val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkStatus.value = NetworkStatus.Available
        }

        override fun onLost(network: Network) {
            _networkStatus.value = NetworkStatus.Lost
        }

        override fun onUnavailable() {
            _networkStatus.value = NetworkStatus.Unavailable
        }
    }

    fun registerCallback() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)
    }

    fun unregisterCallback() {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}
