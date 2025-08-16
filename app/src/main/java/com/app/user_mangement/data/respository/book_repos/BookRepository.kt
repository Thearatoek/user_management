package com.app.user_mangement.data.respository.book_repos

import com.app.user_mangement.data.model.BookModel

interface BookRepostory {

    suspend fun  getBooksListing(): List<BookModel>
    suspend fun getBookById(id: String): BookModel

    suspend fun createBook(book: BookModel): BookModel

    suspend fun updateBook(book: BookModel, id: String): BookModel

    suspend fun deleteBook(id: String): Boolean

}