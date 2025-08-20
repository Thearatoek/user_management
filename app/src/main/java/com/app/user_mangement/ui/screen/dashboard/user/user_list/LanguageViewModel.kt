package com.app.user_mangement.ui.screen.dashboard.user.user_list

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _languageCode = MutableStateFlow("km")
    val languageCode: StateFlow<String> = _languageCode
    init {
        viewModelScope.launch {
            val code = settingsManager.getLang().first()
            code?.let { _languageCode.value = it }
        }
    }
    fun changeLanguage(code: String, context: Context) {
        _languageCode.value = code
        viewModelScope.launch {
            settingsManager.saveLang(code = code)
            (context as? Activity)?.recreate()
        }
    }
}
