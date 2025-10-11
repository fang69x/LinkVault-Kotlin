package com.fang.linkvault.data.api

import com.fang.linkvault.data.dto.auth.LoginRequestDto
import com.fang.linkvault.data.dto.auth.LoginResponseDto
import com.fang.linkvault.data.dto.auth.RegisterRequestDto
import com.fang.linkvault.data.dto.auth.RegisterResponseDto
import com.fang.linkvault.data.dto.auth.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import retrofit2.http.Part

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @Multipart
    @POST("api/auth/register")
    suspend fun register(
        @Part name: MultipartBody.Part,
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part,
        @Part avatar: MultipartBody.Part? = null
    ): RegisterResponseDto


    @GET("api/auth/me")
    suspend fun checkAuthStatus():UserDto
    @POST()
    suspend fun logout (): Response<Unit>
}
