package com.fang.linkvault.data.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fang.linkvault.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookmark: List<BookmarkEntity>)

    @Query("SELECT *FROM bookmarks")
    fun getAllBookmarks():Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getBookmarksById(id:String):BookmarkEntity

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmarkById(id:String)

    @Query("DELETE FROM bookmarks")
    suspend fun clearAll()
}