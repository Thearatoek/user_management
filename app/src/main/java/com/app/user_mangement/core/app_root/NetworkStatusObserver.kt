package com.app.user_mangement.core.app_root

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.app.user_mangement.core.NetworkMonitor
import com.app.user_mangement.core.NetworkStatus

@Composable
fun NetworkStatusObserver(networkMonitor: NetworkMonitor) {
    val status by networkMonitor.networkStatus.collectAsState()
    val context = LocalContext.current

    if (status != NetworkStatus.Available) {
        LaunchedEffect(status) {
            Toast.makeText(
                context,
                "⚠️ No internet connection",
                Toast.LENGTH_SHORT
            ).show()
        }
    }else {
        LaunchedEffect(status) {
            Toast.makeText(
                context,
                "✅ Back online",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
