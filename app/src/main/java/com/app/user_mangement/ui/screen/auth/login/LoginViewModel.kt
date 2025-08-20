package com.app.user_mangement.ui.screen.auth.login
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.UserModel
import org.example.user.management.sample.data.respository.local.UserRepository
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
   private val userRepository: UserRepository
) : ViewModel(){
    private val _user = MutableStateFlow<UserModel?>(null)
    val user : StateFlow<UserModel?> get() = _user.asStateFlow()
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState
    fun  loginWithEmailAndPassword(email : String , password: String){
        viewModelScope.launch{
           _loginState.value = LoginUiState.Loading
            val response = userRepository.login(email =  email, password = password)
           response.onSuccess {
               _loginState.value = LoginUiState.Success
           }
               .onFailure { e->
                   _loginState.value = LoginUiState.Error(message = e.message.toString())
               }
        }
    }
    fun logout(){
        viewModelScope.launch {
            userRepository.logout()
            _loginState.value = LoginUiState.Success
        }
    }
   fun signUpUserWithEmailAndPassword(email : String , password: String, username : String){
       viewModelScope.launch {
           _loginState.value = LoginUiState.Loading
           val result = userRepository.signUpUserWithEmailAndPassword(userModel = UserModel(
               email  = email, password = password, name = username
           ))
           result.onSuccess {
               _loginState.value = LoginUiState.Success
           }
           result.onFailure {e->
               _loginState.value = LoginUiState.Error(message = e.message.toString())
           }
       }
   }
    // get user
    fun getUserData() {
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            val result = userRepository.getUserFirebase()
            result.onSuccess { user->
                _user.value = user
                _loginState.value = LoginUiState.Success
            }
            result.onFailure {e->
                _loginState.value = LoginUiState.Error(message = e.message.toString())
            }

        }
    }
    // update user
    fun updateUser(name : String) {
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            userRepository.updateUser(name = name)
            _loginState.value = LoginUiState.Success
        }
    }

}
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

