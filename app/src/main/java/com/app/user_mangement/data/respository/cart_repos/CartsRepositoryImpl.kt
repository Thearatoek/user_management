package com.app.user_mangement.data.respository.cart_repos

import android.util.Log
import com.app.user_mangement.data.datasource.remote.ApiInterface
import com.app.user_mangement.data.model.CartResponse
import com.app.user_mangement.data.model.OrderResponse
import javax.inject.Inject

class CartsRepositoryImpl @Inject constructor (
    private val apiInterface: ApiInterface
) : CartRepository {
    override suspend fun getCartData():CartResponse {
        try {
            val response = apiInterface.getCarts()
            Log.d("CartsRepositoryImpl", "Response: $response")
                return response

        }catch (e: Exception) {
            throw Exception("Error fetching cart data: ${e.message}")
        }
    }
}