package org.example.user.management.sample.di

import android.content.Context
import com.app.user_mangement.data.datasource.local.datastore.UserManagerStore
import com.app.user_mangement.data.datasource.remote.ApiInterface
import com.app.user_mangement.data.respository.book_repos.BookRepositoryImpl
import com.app.user_mangement.data.respository.book_repos.BookRepostory
import com.app.user_mangement.data.respository.cart_repos.CartRepository
import com.app.user_mangement.data.respository.cart_repos.CartsRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.example.user.management.sample.data.respository.local.UserRepository
import org.example.user.management.sample.data.respository.local.UserRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        userLocalDataManager: UserManagerStore,
        @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(
            auth,
            firestore,
            userLocalDataManager,
            context)
    }

    @Singleton
    @Provides
    fun provideCartRepository(apiInterface: ApiInterface): CartRepository {
        return CartsRepositoryImpl(
            apiInterface = apiInterface
        )
    }

    //register book repository
    @Singleton
    @Provides
    fun provideBookRepository(apiInterface: ApiInterface): BookRepostory {
        return BookRepositoryImpl(
            apiInterface = apiInterface
        )
    }
}