package org.example.user.management.sample.data.respository.local

import com.google.android.gms.common.api.Response
import org.example.user.management.sample.data.model.UserModel

interface UserRepository {

    suspend fun login(email : String , password : String) : Result<String>

   suspend fun getUserFirebase(): Result<UserModel>

    suspend fun checkUserLogin(): Boolean

    suspend fun  logout() : Boolean

    suspend fun signUpUserWithEmailAndPassword(userModel: UserModel) :Result<String>
    suspend fun updateUser(name: String)
}