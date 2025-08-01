package org.example.user.management.sample.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey
    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("email")
    val email: String, // ✅ Required

    @SerializedName("phone")
    val phone: String = "",

    @SerializedName("password")
    val password: String, // ✅ Required

    @SerializedName("age")
    val age: Int? = null,

    @SerializedName("status")
    val status: Boolean? = null
)

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()

    data class Error(val message: String) : LoginUiState()
}
