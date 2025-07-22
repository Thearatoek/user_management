package org.example.user.management.sample.data.respository.local

import org.example.user.management.sample.data.datasource.local.dao.UserDao
import org.example.user.management.sample.data.model.UserModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getAllUsers(): List<UserModel> {
        return  userDao.getAllUsers()
    }

    override suspend fun addUser(user: UserModel): UserModel {
        val response = userDao.getUserById(user.id)
        // If the user already exists, update it
        if (response != null) {
            return updateUserById(id = user.id, user = user)
        }

        userDao.insert(user)

        return user
    }

    override suspend fun updateUserById(
        id: Int,
        user: UserModel
    ): UserModel {
        val response = userDao.getUserById(id)

        // If the user exists, update it
        if (response != null) {
            userDao.updateUserById(user)
        } else {
            // If the user does not exist, insert it
            return addUser(user)
        }

        // If the user does not exist, return the original user
        return user
    }

    override suspend fun removeUserById(id: Int): Boolean {
        val response = userDao.getUserById(id)

        // If the user exists, delete it
        if (response != null) {
            userDao.deleteUserById(id)
            return true;
        }

        // If the user does not exist, return false
        return false;
    }

    override suspend fun getUserById(id: Int): UserModel? {
        return userDao.getUserById(id)
    }
}