package org.example.user.management.sample.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.example.user.management.sample.data.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserModel): Long

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): UserModel?

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Int)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserById(user: UserModel): Int


}