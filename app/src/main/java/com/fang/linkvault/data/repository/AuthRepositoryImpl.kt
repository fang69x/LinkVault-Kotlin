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
import com.fang.linkvault.data.dto.auth.RegisterResponseDto
import com.fang.linkvault.domain.model.User
import com.fang.linkvault.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import toDomain
import java.io.File
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
        password: String,
        avatarUrl:String?
    ): Result<RegisterResponseDto> {
        return try{
            val namePart= name.toRequestBody("text/plain".toMediaType())
            val emailPart= email.toRequestBody("text/plain".toMediaType())
            val passwordPart= password.toRequestBody("text/plain".toMediaType())
            val avatarPart= avatarUrl?.takeIf { it.isNotEmpty() }?.let { path->
                val file= File(path)
                val requestFile= file.asRequestBody("image/*".toMediaType())
                MultipartBody.Part.createFormData("avatar", file.name,requestFile)
            }
            val response=apiService.register(namePart,emailPart,passwordPart,avatarPart)
            TokenStorage.saveToken(context, response.token)
            return Result.success(response)
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
            TokenStorage.clearToken(context)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}