package org.example.user.management.sample.di

import android.content.Context
import com.app.user_mangement.data.datasource.local.datastore.SettingsManager
import com.app.user_mangement.data.datasource.local.datastore.UserManagerStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideUserManagerStore(
        @ApplicationContext context: Context
    ): UserManagerStore {
        return UserManagerStore
    }
    @Provides
    @Singleton
    fun provideSettingsManager(
        @ApplicationContext context: Context
    ): SettingsManager = SettingsManager(context)
}
