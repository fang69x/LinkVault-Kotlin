package com.fang.linkvault.domain.repository

import com.fang.linkvault.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
     fun getBookmark():Flow<List<Bookmark>>
    suspend fun refreshBookmark(page:Int,limit: Int):Result<Unit>
    suspend fun getBookmarksById(id:String):Result<Bookmark>
    suspend fun createBookmark(bookmark: Bookmark): Result<Bookmark>
    suspend fun updateBookmark(id:String,bookmark: Bookmark):Result<Bookmark>
    suspend fun deleteBookmark(id:String): Result<Boolean>
    suspend fun searchBookmark(query:String? ,category:String?,page:Int,limit:Int):Result<List<Bookmark>>
}