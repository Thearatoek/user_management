package com.app.user_mangement.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object UserManagerStore {

    private val TOKEN_KEY = stringPreferencesKey("user_token")

    /** Save token */
    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    /** Clear token */
    suspend fun clearToken(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    /** Get token as Flow */
    fun getTokenFlow(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY]
        }
    }

    /** Get token once (blocking, for startDestination) */
    suspend fun getTokenOnce(context: Context): String? {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY]
        }.first()
    }
}
