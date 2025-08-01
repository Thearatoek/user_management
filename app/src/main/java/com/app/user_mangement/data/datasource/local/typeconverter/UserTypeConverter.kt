package org.example.user.management.sample.data.datasource.local.typeconverter

import androidx.room.Dao
import androidx.room.TypeConverter
import com.google.gson.Gson


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