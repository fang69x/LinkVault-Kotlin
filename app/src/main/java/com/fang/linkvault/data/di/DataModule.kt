package com.fang.linkvault.data.di

import com.fang.linkvault.data.di.LinkVaultDatabase
import android.content.Context
import androidx.room.Room
import com.fang.linkvault.data.api.AuthApiService
import com.fang.linkvault.data.api.BookmarkApiService
import com.fang.linkvault.data.di.DataModule.BASE_URL
import com.fang.linkvault.data.local.BookmarkDao
import com.fang.linkvault.data.repository.AuthRepositoryImpl
import com.fang.linkvault.data.repository.BookmarkRepositoryImpl
import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.domain.repository.AuthRepository
import com.fang.linkvault.domain.repository.BookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://linkvault-bti4.onrender.com"
    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideAuthApiService (retrofit: Retrofit): AuthApiService{
        return retrofit.create(AuthApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideBookmarkApiService(retrofit: Retrofit): BookmarkApiService{
        return retrofit.create(BookmarkApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLinkVaultDatabase(@ApplicationContext context: Context): LinkVaultDatabase {
        return Room.databaseBuilder(
            context,
            LinkVaultDatabase::class.java,
            "linkvault.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao (database :LinkVaultDatabase): BookmarkDao{
        return database.bookmarkDao()
    }
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: AuthApiService): AuthRepository{
        return AuthRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideBookmarkRepository(apiService: BookmarkApiService, bookmarkDao: BookmarkDao): BookmarkRepository{
        return BookmarkRepositoryImpl(bookmarkDao,apiService)
    }

}