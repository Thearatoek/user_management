package com.app.user_mangement.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import kotlinx.coroutines.flow.map

class SettingsManager(private val context: Context) {

    companion object {
        private val THEME_KEY = stringPreferencesKey("theme_mode")
        private val LANG_KEY = stringPreferencesKey("lang_code")
    }

    suspend fun saveTheme(code: String) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = code
        }
    }

    fun getTheme(): Flow<String> =
        context.dataStore.data.map { prefs -> prefs[THEME_KEY] ?: "light" }

    suspend fun saveLang(code: String) {
        context.dataStore.edit { prefs ->
            prefs[LANG_KEY] = code
        }
    }

    fun getLang(): Flow<String> =
        context.dataStore.data.map { prefs -> prefs[LANG_KEY] ?: "en" }

}