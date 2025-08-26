package com.app.user_mangement.di

import com.app.user_mangement.data.datasource.remote.ApiInterface
import com.app.user_mangement.data.respository.firebse_messaging.FirebaseCloudMessagingRepository
import com.app.user_mangement.data.respository.firebse_messaging.FirebaseCloudMessagingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
    @Provides
    @Singleton
    fun provideFirebaseCloudMessagingRepository(
        api: ApiInterface
    ): FirebaseCloudMessagingRepository {  // ⚡ return type is interface
        return FirebaseCloudMessagingRepositoryImpl(api)
    }
}