package org.example.user.management.sample.data.respository.local

import android.content.Context
import android.util.Log
import com.app.user_mangement.data.datasource.local.datastore.UserLocalDataManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.example.user.management.sample.data.model.UserModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebase :FirebaseFirestore,
    private val userLocalDataManager: UserLocalDataManager,
    private val context: Context
) : UserRepository {
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("error", "${result.user}")
            val user = result.user
            if(user !=null){
                // Save user key as Local
                userLocalDataManager.saveUserTokenKey(context, user.uid)
            }
           return  Result.success(user?.uid ?:"" )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getUserFirebase(): Result<UserModel> = withContext(Dispatchers.IO) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                val snapshot = firebase.collection("users").document(currentUser.uid).get().await()
                if (snapshot.exists()) {
                    val user = snapshot.toObject(UserModel::class.java)
                    if (user != null) {
                        Result.success(user)
                    } else {
                        Result.failure(Exception("User object is null"))
                    }
                } else {
                    Result.failure(Exception("User document not found"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("User not authenticated"))
        }
    }
    override suspend fun checkUserLogin(): Boolean {
        val start = System.currentTimeMillis()
        val userKey =userLocalDataManager.getUserTokenKey(context)
        val end = System.currentTimeMillis()
        Log.d("StorageTime", "Read time: ${end - start} ms")
        return userKey?.isNotEmpty() == true
    }
    override suspend fun logout():Boolean {
        try {
            auth.signOut()
            userLocalDataManager.clearUserTokenKey(context)
            userLocalDataManager.clearUserTokenKey(context)
            // update state
        } catch (e: Exception) {
            //
        }
        return true
    }
    override suspend fun signUpUserWithEmailAndPassword(userModel: UserModel): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(userModel.email, userModel.password).await()
            Log.d("BekMa", "${result.user}")
            val user = result.user
            if (user != null) {
                val users = UserModel(
                    id = user.uid,
                    name = userModel.name,
                    email = userModel.email,
                    password = ""
                )
                // Add user to "users" collection with UID as document ID
                firebase.collection("users").document(user.uid).set(users).await()

                // Save key as Local
                userLocalDataManager.saveUserTokenKey(context, user.uid)
                Result.success(user.uid)
            } else {
                Result.failure(Exception("Login failed: User is null"))
            }
        } catch (e: Exception) {
            Log.d("BekMa", "${e.message}")
            Result.failure(e)
        }
    }


}