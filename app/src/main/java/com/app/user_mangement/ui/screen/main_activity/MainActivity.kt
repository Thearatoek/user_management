package org.example.user.management.sample.ui.screen.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.user_mangement.core.NetworkMonitor
import com.app.user_mangement.core.app_root.NetworkStatusObserver
import com.app.user_mangement.data.datasource.local.datastore.UserManagerStore
import com.app.user_mangement.navigation.AppNavGraph
import com.app.user_mangement.ui.screen.user.user_list.ThemeModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import org.example.user.management.sample.ui.theme.MyAppTheme
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userManagerStore: UserManagerStore
    @Inject
    lateinit var networkMonitor: NetworkMonitor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeModeViewModel = hiltViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            // Register network status observer
            networkMonitor.registerCallback()
            MyAppTheme(themeMode = themeMode) {
                // Compose state for startDestination
                var startDestination by remember { mutableStateOf<String?>(null) }
                // Fetch token and set startDestination
                LaunchedEffect(Unit) {
                    val token = userManagerStore.getTokenFlow(context = baseContext).firstOrNull() ?: ""
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
