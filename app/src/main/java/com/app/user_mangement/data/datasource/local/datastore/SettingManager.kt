package com.app.user_mangement.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SettingsManager {
    private val KEY_LANGUAGE = stringPreferencesKey("language_code")
    private val KEY_THEME = stringPreferencesKey("theme_mode")
    fun getLanguageFlow(context: Context): Flow<String> {
        return context.dataStore.data
            .map { prefs -> prefs[KEY_LANGUAGE] ?: "en" }
    }
    fun getThemeFlow(context: Context): Flow<String> {
        return context.dataStore.data
            .map { prefs -> prefs[KEY_THEME] ?: "system" }
    }
    suspend fun saveLanguage(context: Context, langCode: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LANGUAGE] = langCode
        }
    }
    suspend fun saveTheme(context: Context, theme: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME] = theme
        }
    }
}
