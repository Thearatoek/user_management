package org.example.user.management.sample.ui.screen.main_activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.app.user_mangement.core.AppLanguageManager
import com.app.user_mangement.core.NetworkMonitor
import com.app.user_mangement.core.app_root.NetworkStatusObserver
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import com.app.user_mangement.data.datasource.local.datastore.UserManagerStore
import com.app.user_mangement.navigation.AppNavGraph
import com.app.user_mangement.ui.screen.dashboard.user.user_list.LanguageViewModel
import com.app.user_mangement.ui.screen.dashboard.user.user_list.ThemeModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.user.management.sample.ui.theme.MyAppTheme
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userManagerStore: UserManagerStore
    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private val viewModel: LanguageViewModel by viewModels()
    override fun attachBaseContext(base: Context) {
        val langCode = runBlocking { SettingsManager(base).getLang().first() }
        val newContext = AppLanguageManager.applyLanguage(base, langCode)
        super.attachBaseContext(newContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeModeViewModel = hiltViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            networkMonitor.registerCallback()
            MyAppTheme(themeMode = themeMode) {
                // Compose state for startDestination
                var startDestination by remember { mutableStateOf<String?>(null) }
                val code by viewModel.languageCode.collectAsState()
                LaunchedEffect(Unit) {
                    val token = userManagerStore
                        .getTokenFlow(context = this@MainActivity)
                        .firstOrNull() ?: ""
                     AppLanguageManager.applyLanguage(baseContext, code)

                    startDestination = if (token.isNotEmpty()) "dashboard" else "welcome_screen"
                }
                startDestination?.let { start ->
                    AppNavGraph(startDestination = start)
                }
                NetworkStatusObserver(networkMonitor)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.unregisterCallback()
    }
}
