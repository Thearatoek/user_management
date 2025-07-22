package org.example.user.management.sample.data.datasource.local.typeconverter

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.Update
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.example.user.management.sample.data.model.UserModel

@Dao
class UserTypeConverter {
    private val gson : Gson by lazy {
        Gson()
    }

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.let {
            com.google.gson.Gson().fromJson(it, object : com.google.gson.reflect.TypeToken<List<String>>() {}.type)
        }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return gson.toJson(list)
    }

}