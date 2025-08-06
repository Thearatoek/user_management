package com.app.user_mangement.ui.screen.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.LoginUiState
import org.example.user.management.sample.data.model.UserModel
import org.example.user.management.sample.data.respository.local.UserRepository
import javax.inject.Inject
@HiltViewModel

class LoginViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel(){
    // login function
    init {
       viewModelScope.launch {
           userRepository.checkUserLogin()
       }
    }
    private val _listingPage = MutableStateFlow<List<String>>(emptyList())
    val listingPage : StateFlow<List<String>> = _listingPage.asStateFlow()
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
    fun checkUserLoginAction() {
        viewModelScope.launch {
            val result = userRepository.checkUserLogin()
            if (result) {
                _loginState.value = LoginUiState.Success
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
}