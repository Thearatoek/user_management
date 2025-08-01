package com.app.user_mangement.ui.screen.user.user_list

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.core.AppLanguageManager
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LanguageViewModel(
    application: Application,
    ) : AndroidViewModel(application) {
    private val _languageCode = MutableStateFlow("km")
    val languageCode: StateFlow<String> = _languageCode
    init {
        viewModelScope.launch {
            val code = SettingsManager.getLanguageFlow(application).first()
            code?.let { _languageCode.value = it }
        }
    }
    fun changeLanguage(code: String, context: Context) {
        _languageCode.value = code
        viewModelScope.launch {
            SettingsManager.saveLanguage(getApplication(), code)
            AppLanguageManager.applyLanguage(getApplication(), code)
            (context as? Activity)?.recreate()
        }
    }
}