package org.example.user.management.sample.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.example.user.management.sample.data.datasource.local.dao.UserDao
import org.example.user.management.sample.data.datasource.local.typeconverter.UserTypeConverter
import org.example.user.management.sample.data.model.UserModel


@TypeConverters(UserTypeConverter::class)
@Database(version = 1, entities = [UserModel::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}