package com.fang.linkvault.data.di
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fang.linkvault.data.local.Converters
import androidx.room.TypeConverters
import com.fang.linkvault.data.local.BookmarkDao
import com.fang.linkvault.data.local.BookmarkEntity
import retrofit2.Converter

@Database(
entities = [BookmarkEntity::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class LinkVaultDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}