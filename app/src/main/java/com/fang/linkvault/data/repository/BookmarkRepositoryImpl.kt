package com.fang.linkvault.data.repository

import com.fang.linkvault.data.api.BookmarkApiService
import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import toDomain
import toDto

class BookmarkRepositoryImpl(
    private val apiService: BookmarkApiService
) : BookmarkRepository{
    override  fun getBookmark(): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshBookmark(
        page: Int,
        limit: Int
    ): Result<Unit> {
      return try{
          val bookmakrsFromApi = apiService.getBookmarks(page,limit)
          Result.success(Unit)
      }catch (e: Exception){
          Result.failure(e)
      }
    }

    override suspend fun getBookmarksById(id: String): Result<Bookmark> {
        return try{
            val bookmarkDto= apiService.getBookmarkById(id)
            Result.success(bookmarkDto.toDomain())
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun createBookmark(bookmark: Bookmark): Result<Bookmark> {
       return  try{
           val newBookmarkDto = apiService.createBookmark(bookmark.toDto())
           Result.success(newBookmarkDto.toDomain())
        }catch (e:Exception){
           Result.failure(e)
       }
    }

    override suspend fun updateBookmark(id:String,bookmark: Bookmark): Result<Bookmark> {
        return try {
            val updatedBookmarkDto = apiService.updateBookmark(id, bookmark.toDto())
            Result.success(updatedBookmarkDto.updatedBookmark.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteBookmark(
        id: String,

    ): Result<Boolean> {
    return try{
val response = apiService.deleteBookmark(id)
        Result.success(response.isSuccessful)
    }catch(e:Exception){
        Result.failure(e)
        }
    }

    override suspend fun searchBookmark(
        query: String?,
        category: String?,
        page: Int,
        limit: Int
    ): Result<List<Bookmark>> {
       return try{
           val searchResponse = apiService.searchBookmarks(query!!,category!!,page,limit)
           val domainBookmarks= searchResponse.bookmarks.map { it.toDomain() }
           Result.success(domainBookmarks)
       }catch(e:Exception){
           Result.failure(e)
       }
    }

}