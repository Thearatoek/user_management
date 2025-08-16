package com.app.user_mangement.data.respository.cart_repos

import com.app.user_mangement.data.model.CartResponse

interface CartRepository {
    suspend fun getCartData():CartResponse
}