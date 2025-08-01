package org.example.user.management.sample.ui.screen.main_activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.user_mangement.core.AppLanguageManager
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import com.app.user_mangement.navigation.AppNavGraph
import com.app.user_mangement.ui.screen.user.user_list.ThemeModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.example.user.management.sample.ui.theme.MyAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(base: Context) {
        val langCode = runBlocking {
            SettingsManager.getLanguageFlow(base).first()
        }
        val newContext = AppLanguageManager.applyLanguage(base, langCode)
        super.attachBaseContext(newContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeModeViewModel = hiltViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            MyAppTheme(themeMode = themeMode) {
                AppNavGraph()
            }
        }
    }
}