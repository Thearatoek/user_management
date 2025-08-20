package org.example.user.management.sample.data.respository.local

import android.content.Context
import android.util.Log
import com.app.user_mangement.data.datasource.local.datastore.UserManagerStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.example.user.management.sample.data.model.UserModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebase :FirebaseFirestore,
    private val userManager: UserManagerStore,
    private val context: Context
) : UserRepository {
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("error", "${result.user}")
            val user = result.user
            if(user !=null){
                // Save user key as Local
                userManager.saveToken(context, user.uid)
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

        // Assuming getTokenFlow returns a Flow<String?>
        val userKey = userManager.getTokenFlow(context).firstOrNull() ?: ""

        val end = System.currentTimeMillis()
        Log.d("StorageTime", "Read time: ${end - start} ms")

        // Return true if userKey is not empty (logged in), false otherwise
        return userKey.isNotEmpty()
    }

    override suspend fun logout():Boolean {
        try {
            auth.signOut()
            userManager.clearToken(context)         // update state
        } catch (e: Exception) {
            //
        }
        return true
    }
    override suspend fun signUpUserWithEmailAndPassword(userModel: UserModel): Result<String> {
        return try {
            val email = userModel.email ?: return Result.failure(Exception("Email is null"))
            val password = userModel.password ?: return Result.failure(Exception("Password is null"))
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                val newUser = UserModel(
                    id = user.uid,
                    name = userModel.name,
                    email = userModel.email,
                    password = "" // Don't store password in Firestore
                )

                // Save user to Firestore (document id = uid)
                firebase.collection("users")
                    .document(user.uid)
                    .set(newUser)
                    .await()

                // Save uid locally (SharedPref / DataStore)
                userManager.saveToken(context, user.uid)

                Result.success(user.uid) // return String (uid)
            } else {
                Result.failure(Exception("SignUp failed: user is null"))
            }
        } catch (e: Exception) {
            Log.e("SignUp", "Error: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateUser(name: String) {
        val currentUser = auth.currentUser
        try {
            if (currentUser != null) {
                firebase.collection("users").document(currentUser.uid).update(
                    mapOf(
                        "name" to name,
                    )
                ).await()
            }
        } catch (e: Exception) {
            Log.d("BekMa", "${e.message}")
        }
    }

}