package com.app.user_mangement.ui.screen.user.user_list

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThemeModeViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val _themeMode = MutableStateFlow("light_mode")
    val themeMode: StateFlow<String> = _themeMode
    init {
        viewModelScope.launch {
            val code = SettingsManager.getThemeFlow(application).first()
            code.let { _themeMode.value = it }
        }
    }
    fun changeThemeMode(code: String, context: Context) {
        _themeMode.value = code
        viewModelScope.launch {
            SettingsManager.saveTheme(getApplication(), code)
            (context as? Activity)?.recreate()
        }
    }

}