package com.fang.linkvault.data.api

import retrofit2.http.GET
import retrofit2.http.Query

import com.fang.linkvault.data.dto.bookmark.BookmarkDto
import com.fang.linkvault.data.dto.bookmark.SearchResponseDto
import com.fang.linkvault.data.dto.bookmark.UpdateBookmarkResponseDto
import com.fang.linkvault.domain.model.Bookmark
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BookmarkApiService {
    @GET("api/bookmarks")
    suspend fun getBookmarks(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<BookmarkDto>

  @GET("api/bookmarks/{id}")
  suspend fun getBookmarkById(@Path("id") id:String): BookmarkDto

  @POST("api/bookmarks")
  suspend fun createBookmark(@Body bookmark: BookmarkDto): BookmarkDto

    @PUT("api/bookmarks/{id}")
    suspend fun updateBookmark(
        @Path("id")  id:String,
        @Body bookmark: BookmarkDto
    ): UpdateBookmarkResponseDto

    @DELETE("api/bookmarks/{id}")
    suspend fun deleteBookmark(@Path ("id") id:String) : Response<Unit>

    @GET("api/bookmarks/search")
    suspend fun searchBookmarks(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("limit") limit :Int
    ): SearchResponseDto

}