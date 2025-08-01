package org.example.user.management.sample.ui.screen.user.user_list

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.UserModel
import org.example.user.management.sample.data.respository.local.UserRepository
import java.time.Duration
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _userList  = MutableStateFlow<List<UserModel>>(arrayListOf())
    val userList  : StateFlow<List<UserModel>> get() = _userList.asStateFlow()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()
    private  val _user = MutableStateFlow<UserModel?>(null)
    val user : StateFlow<UserModel?> get() = _user.asStateFlow()

    private val _isRefresh = MutableStateFlow<Boolean>(false)
    val isRefresh : StateFlow<Boolean> get() = _isRefresh.asStateFlow()



}
