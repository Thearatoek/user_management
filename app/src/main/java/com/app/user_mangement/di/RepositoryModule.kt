package org.example.user.management.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.example.user.management.sample.data.datasource.local.dao.UserDao
import org.example.user.management.sample.data.respository.local.UserRepository
import org.example.user.management.sample.data.respository.local.UserRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // user repository
    @Singleton
    @Provides
    fun providerUserRepository(
        userDao: UserDao,
    ): UserRepository {
        return UserRepositoryImpl(
            userDao
        )
    }

}