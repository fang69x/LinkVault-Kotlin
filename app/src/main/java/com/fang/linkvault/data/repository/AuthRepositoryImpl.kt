package com.fang.linkvault.data.repository

import android.content.Context
import android.util.Log
import androidx.annotation.UiContext
import androidx.annotation.experimental.Experimental
import com.fang.linkvault.data.api.AuthApiService
import com.fang.linkvault.data.api.BookmarkApiService
import com.fang.linkvault.data.api.TokenStorage
import com.fang.linkvault.data.dto.ApiError
import com.fang.linkvault.data.dto.ApiErrorParser
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

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val request = LoginRequestDto(email, password)
            val response = apiService.login(request)

            if (response.isSuccessful) {
                val body = response.body()!!
                Log.d("AuthRepository", "Server response: $body")
                TokenStorage.saveToken(context, body.token)
                Log.d("AuthRepository", "Token saved, returning success")
                Result.success(body.user.toDomain())
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage= ApiErrorParser.parseError(errorBody)
                Log.e("AuthRepository", "Server error: $errorBody")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Exception during login", e)
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
            val namePart= MultipartBody.Part.createFormData("name",name)
            val emailPart= MultipartBody.Part.createFormData("email",email)
            val passwordPart= MultipartBody.Part.createFormData("password",password)

            val avatarPart= avatarUrl?.takeIf { it.isNotEmpty() }?.let { path->
                val file= File(path)
                if(file.exists()){
                    val requestFile = file.asRequestBody("image/*".toMediaType())
                    MultipartBody.Part.createFormData("avatar", file.name, requestFile)
                }else{
                    null
                }
            }
            val response=apiService.register(namePart,emailPart,passwordPart,avatarPart)
            if(response.isSuccessful){
                val body = response.body()!!
                TokenStorage.saveToken(context, body.token)
                return Result.success(body)
            }else {
                val errorBody = response.errorBody()?.string()
                val errorMessage= ApiErrorParser.parseError(errorBody)
                Log.e("AuthRepository", "Register failed: $errorBody")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Exception during register", e)
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