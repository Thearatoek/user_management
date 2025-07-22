package org.example.user.management.sample.ui.screen.user.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.UserModel
import org.example.user.management.sample.data.respository.local.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _userList = MutableStateFlow<List<UserModel>>(arrayListOf())
    val userList get() = _userList.asStateFlow()

    private  val _user = MutableStateFlow<UserModel?>(null)
    val user get() = _user.asStateFlow()

    fun fetchUsers() {
        viewModelScope.launch {
            _userList.value = userRepository.getAllUsers()
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            userRepository.removeUserById(userId)
            fetchUsers() // Refresh the list after deletion
        }
    }
    fun updateUser(user: UserModel) {
        viewModelScope.launch {
            userRepository.updateUserById(user.id, user)
            fetchUsers() // Refresh the list after updating
        }
    }
// Function to add a new user
    fun addUser(user: UserModel) {
        viewModelScope.launch {
            userRepository.addUser(user)
            fetchUsers() // Refresh the list after adding
        }
    }

    // get user by Id with flow
    fun getUserById(userId: Int): Flow<UserModel?> {
        return flow {
            emit(userRepository.getUserById(userId))
        }
    }

    // mock add user function
    fun mockAddUser() {
        viewModelScope.launch {
            val mockUser = UserModel(
                name = "Random User ${System.currentTimeMillis()}",
                email = "random${System.currentTimeMillis()}@example.com",
                phone = "1234567890",
                age = (18..60).random()
            )
            userRepository.addUser(mockUser)
            fetchUsers() // Refresh the list after adding
        }
    }
}
