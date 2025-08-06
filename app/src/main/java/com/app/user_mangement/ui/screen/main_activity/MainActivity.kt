package org.example.user.management.sample.ui.screen.main_activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.user_mangement.core.AppLanguageManager
import com.app.user_mangement.core.NetworkMonitor
import com.app.user_mangement.core.app_root.NetworkStatusObserver
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import com.app.user_mangement.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(base: Context) {
        val langCode = runBlocking {
            SettingsManager.getLanguageFlow(base).first()
        }
        val newContext = AppLanguageManager.applyLanguage(base, langCode)
        super.attachBaseContext(newContext)
    }
    @Inject
    lateinit var networkMonitor: NetworkMonitor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkMonitor.registerCallback()
        setContent {
            setContent {
                AppNavGraph()
                NetworkStatusObserver(networkMonitor)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.unregisterCallback()
    }
}

