package com.fang.linkvault.data.repository

import androidx.annotation.experimental.Experimental
import com.fang.linkvault.data.api.AuthApiService
import com.fang.linkvault.data.api.BookmarkApiService
import com.fang.linkvault.data.dto.auth.LoginRequestDto
import com.fang.linkvault.data.dto.auth.LoginResponseDto
import com.fang.linkvault.data.dto.auth.RegisterRequestDto
import com.fang.linkvault.domain.model.User
import com.fang.linkvault.domain.repository.AuthRepository
import toDomain
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
) : AuthRepository{
    override suspend fun login(email:String,password:String) : Result<User>{
        return try{
            val request = LoginRequestDto(email,password)
            val response =  apiService.login(request)
            return Result.success(response.user.toDomain())

        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try{
            val request= RegisterRequestDto(name, email,password)
            apiService.register(request)
            return Result.success(Unit)
        }catch (e:kotlin.Exception){
            Result.failure(e)
        }
    }

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
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}