package com.fang.linkvault.data.repository

import android.content.Context
import androidx.annotation.UiContext
import androidx.annotation.experimental.Experimental
import com.fang.linkvault.data.api.AuthApiService
import com.fang.linkvault.data.api.BookmarkApiService
import com.fang.linkvault.data.api.TokenStorage
import com.fang.linkvault.data.dto.auth.LoginRequestDto
import com.fang.linkvault.data.dto.auth.LoginResponseDto
import com.fang.linkvault.data.dto.auth.RegisterRequestDto
import com.fang.linkvault.domain.model.User
import com.fang.linkvault.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import toDomain
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    @ApplicationContext private val context: Context
) : AuthRepository{

    override suspend fun login(email:String,password:String) : Result<User>{
        return try{
            val request = LoginRequestDto(email,password)
            val response =  apiService.login(request)
            TokenStorage.saveToken(context,response.token)
            return Result.success(response.user.toDomain())

        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
        //Todo("The avatar path is to be added here )
    ): Result<Unit> {
        return try{
            val request= RegisterRequestDto(name, email,password)
            val response=apiService.register(request)
//            TokenStorage.saveToken(context,response.token)
            return Result.success(Unit)
        }catch (e:kotlin.Exception){
            Result.failure(e)
        }
    }
    //Todo(" Register does not have the access token yet ")
    override suspend fun checkAuthStatus(): Result<User> {
        return try {
            val userDto=apiService.checkAuthStatus()
            Result.success(userDto.toDomain())
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            apiService.logout()
            TokenStorage.clearToken(context)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}