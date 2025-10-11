package com.fang.linkvault.data.repository

import com.fang.linkvault.data.api.BookmarkApiService
import com.fang.linkvault.data.local.BookmarkDao
import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toDomain
import toDto
import toEntity
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val apiService: BookmarkApiService
) : BookmarkRepository{


    override  fun getBookmark(): Flow<List<Bookmark>> {
        return bookmarkDao.getAllBookmarks().map{
            entities->
            entities.map {it.toDomain()}
        }
    }

    override suspend fun refreshBookmark(
        page: Int,
        limit: Int
    ): Result<Unit> {
      return try{
          // fresh data from the api of type dto
          val bookmarksFromApi = apiService.getBookmarks(page,limit)
          // map it to roomdb type entity  if the api call is successful
          val bookmarkEntities = bookmarksFromApi.map{
              it.toEntity()
          }
          bookmarkDao.clearAll()
          bookmarkDao.insertAll(bookmarkEntities)
          Result.success(Unit)
      }catch (e: Exception){
          Result.failure(e)
      }
    }

    override suspend fun getBookmarksById(id: String): Result<Bookmark> {
        return try{
            val bookmarkDto= apiService.getBookmarkById(id)
            bookmarkDao.insertAll(listOf(bookmarkDto.toEntity()))
            Result.success(bookmarkDto.toDomain())
        }catch (e:Exception){
            val cachedBookmark= bookmarkDao.getBookmarksById(id)
            if(cachedBookmark!=null){
                Result.success(cachedBookmark.toDomain())
            }
            else {
                Result.failure(e)
            }
        }
    }

    override suspend fun createBookmark(bookmark: Bookmark): Result<Bookmark> {
       return  try{
           // api call to create a bookmark
           val newBookmarkDto = apiService.createBookmark(bookmark.toDto())
           // insert it into the local db if the api call is successful
           bookmarkDao.insertAll(listOf(newBookmarkDto .toEntity()))
           Result.success(newBookmarkDto.toDomain())
        }catch (e:Exception){
           Result.failure(e)
       }
    }

    override suspend fun updateBookmark(id:String,bookmark: Bookmark): Result<Bookmark> {
        return try {
            // api call to update bookmark on server (UpdateBookmarkResponseDto)
            val responseWrapper = apiService.updateBookmark(id, bookmark.toDto())
            // server's response to actual bookmark data (BookmarkDto)
            val updatedBookmarkDto = responseWrapper.updatedBookmark
            // save the updated bookmark to local db
            bookmarkDao.insertAll(listOf(updatedBookmarkDto.toEntity()))

            Result.success(updatedBookmarkDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteBookmark(
        id: String,
    ): Result<Boolean> {
    return try{
val response = apiService.deleteBookmark(id)
        if(response.isSuccessful){
            bookmarkDao.deleteBookmarkById(id)
        }
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