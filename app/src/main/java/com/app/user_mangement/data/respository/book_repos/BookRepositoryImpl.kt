package com.app.user_mangement.data.respository.book_repos

import android.util.Log
import com.app.user_mangement.data.datasource.remote.ApiInterface
import com.app.user_mangement.data.model.BookModel
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private  val apiInterface : ApiInterface
) : BookRepostory{
    override suspend fun getBooksListing(): List<BookModel> {
        try {
            return apiInterface.getBooks()
        } catch (e: Exception) {
            // Handle the exception, log it, or rethrow it as needed
            throw e
        }
    }

    override suspend fun getBookById(id: String): BookModel {
        TODO("Not yet implemented")
    }

    override suspend fun createBook(book: BookModel): BookModel {
        TODO("Not yet implemented")
    }

    override suspend fun updateBook(book: BookModel, id: String): BookModel {
       try {
            apiInterface.updateBook(book.id, book)
       }catch (e: Exception) {
           // Handle the exception, log it, or rethrow it as needed
           throw e
       }
        return book
    }

    override suspend fun deleteBook(id: String): Boolean {
       try {
            apiInterface.deleteBook(id)
       }catch (e: Exception){
           Log.d("Delete error", "can not delete: ${e.message}")
       }
        return true
    }

}