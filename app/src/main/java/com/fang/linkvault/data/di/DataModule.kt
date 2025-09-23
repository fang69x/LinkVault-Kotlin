package com.fang.linkvault.data.di

import com.fang.linkvault.data.di.LinkVaultDatabase
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.simulateHotReload
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
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.OkHttp
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
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        sharedPreferences: SharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = sharedPreferences.getString("auth_token", null)
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                requestBuilder.addHeader("Content-Type", "application/json")
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarkApiService(retrofit: Retrofit): BookmarkApiService {
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
    fun provideBookmarkDao(database: LinkVaultDatabase): BookmarkDao {
        return database.bookmarkDao()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: AuthApiService, @ApplicationContext context: Context): AuthRepository {
        return AuthRepositoryImpl(apiService, context)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(
        apiService: BookmarkApiService,
        bookmarkDao: BookmarkDao
    ): BookmarkRepository {
        return BookmarkRepositoryImpl(bookmarkDao, apiService)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }


}