package org.example.user.management.sample.data.respository.local

import org.example.user.management.sample.data.model.UserModel

interface UserRepository {

    // get all users
    suspend fun getAllUsers(): List<UserModel>

    // add a user
    suspend fun addUser(user: UserModel): UserModel

    // update a user by id
    suspend fun updateUserById(id: Int, user: UserModel): UserModel

    // remove a user by id
    suspend fun removeUserById(id: Int): Boolean

    // get a user by id
    suspend fun getUserById(id: Int): UserModel?

}