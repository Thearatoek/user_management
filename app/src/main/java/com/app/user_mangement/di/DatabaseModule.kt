package org.example.user.management.sample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.example.user.management.sample.data.datasource.local.AppDatabase
import org.example.user.management.sample.data.datasource.local.dao.UserDao
import org.example.user.management.sample.data.respository.local.UserRepository
import org.example.user.management.sample.data.respository.local.UserRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao =
        appDatabase.getUserDao()


    @Singleton
    @Provides
    fun provideUserRepo(userDao: UserDao): UserRepositoryImpl =
        UserRepositoryImpl(userDao)
}