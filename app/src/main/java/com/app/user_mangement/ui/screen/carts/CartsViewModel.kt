package com.app.user_mangement.ui.screen.carts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.user_mangement.data.model.CartResponse
import com.app.user_mangement.data.model.OrderResponse
import com.app.user_mangement.data.model.UiState
import com.app.user_mangement.data.respository.cart_repos.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartsViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _cartState = MutableStateFlow<UiState<CartResponse>>(UiState.Loading)
    val cartState: StateFlow<UiState<CartResponse>> = _cartState
    init {
        fetchCartData()
    }
    fun fetchCartData() {
        viewModelScope.launch {
            _cartState.value = UiState.Loading
            try {
                val result: CartResponse = cartRepository.getCartData()
                _cartState.value = UiState.Success(result)
            } catch (e: Exception) {
                _cartState.value = UiState.Error("Error fetching cart data: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

}