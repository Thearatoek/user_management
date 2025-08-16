package com.app.user_mangement.data.datasource.remote

import com.app.user_mangement.data.model.BookModel
import com.app.user_mangement.data.model.CartResponse
import com.app.user_mangement.data.model.OrderResponse
import com.google.android.gms.common.api.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Singleton

interface ApiInterface {
    @GET("carts")
    suspend fun getCarts(): CartResponse

    @GET("books")
    suspend fun getBooks(): List<BookModel>

    @PUT("books/{id}")
    suspend fun updateBook(
        @Path("id") id: String,
        @Body updatedBook: BookModel
    ):BookModel

    @DELETE("books/{id}")
    suspend fun deleteBook(
        @Path("id") id: String
    ): Boolean
}