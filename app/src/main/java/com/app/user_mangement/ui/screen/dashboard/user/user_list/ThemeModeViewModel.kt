package com.app.user_mangement.ui.screen.dashboard.user.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeModeViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {
    // Flow that always reflects DataStore changes
    val themeMode: StateFlow<String> = settingsManager.getTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "light" // default
        )
    fun changeThemeMode(code: String) {
        viewModelScope.launch {
            settingsManager.saveTheme(code,)
        }
    }
}
