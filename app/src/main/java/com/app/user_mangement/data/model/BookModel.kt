package com.app.user_mangement.data.model
data class BookModel(
    val id: String,
    val createdAt: Long,
    val title: String,
    val subtile: String,
    val author: String,
    val page: Int,
    val publishdate: Long,
    val cover_url: String,
    val language: String,
    val rating: Int,
    val description: String
)
