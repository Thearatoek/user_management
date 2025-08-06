package com.app.user_mangement.data.datasource.local.datastore

import android.content.Context
import android.media.MediaFormat


class UserLocalDataManager(context: Context) {
    // save user key via sharePreferences
    companion object {
        private const val USER_KEY = "user_token"
    }
    val prefs = context.getSharedPreferences("UserKey", Context.MODE_PRIVATE)

    fun saveUserTokenKey(context: Context, userToken: String) {
        prefs.edit().putString(USER_KEY, userToken).apply()
    }

    fun getUserTokenKey(context: Context): String {
        return prefs.getString(USER_KEY, "") ?: ""
    }

    fun clearUserTokenKey(context: Context) {
        prefs.edit().remove(USER_KEY).apply()
    }
}