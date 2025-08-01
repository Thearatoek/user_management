package com.app.user_mangement.data.datasource.local.datastore


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")
