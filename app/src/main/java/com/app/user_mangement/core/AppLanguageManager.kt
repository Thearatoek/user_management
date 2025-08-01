package com.app.user_mangement.core

import android.content.Context
import java.util.Locale
object  AppLanguageManager{
    fun applyLanguage(context: Context, langCode: String): Context {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}