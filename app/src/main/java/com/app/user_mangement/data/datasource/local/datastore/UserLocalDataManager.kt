package com.app.user_mangement.data.datasource.local.datastore

import android.content.Context
import android.media.MediaFormat


class UserLocalDataManager {
    // save user key via sharePreferences
    companion object {
        private const val KEY_LANGUAGE = "user_token"
    }
    fun saveUserTokenKey(context: Context, userToken: String) {
        val prefs = context.getSharedPreferences(MediaFormat.KEY_LANGUAGE, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, userToken).apply()
    }

    fun getUserTokenKey(context: Context): String {
        val prefs = context.getSharedPreferences(MediaFormat.KEY_LANGUAGE, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, "en") ?: "en"
    }

    fun clearUserTokenKey(context: Context) {
        val prefs = context.getSharedPreferences(MediaFormat.KEY_LANGUAGE, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_LANGUAGE).apply()
    }
}