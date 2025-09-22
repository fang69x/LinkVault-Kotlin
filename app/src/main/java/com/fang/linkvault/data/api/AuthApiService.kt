package com.fang.linkvault.data.api

import com.fang.linkvault.data.dto.auth.LoginRequestDto
import com.fang.linkvault.data.dto.auth.LoginResponseDto
import com.fang.linkvault.data.dto.auth.RegisterRequestDto
import com.fang.linkvault.data.dto.auth.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<Unit>
    @GET("api/auth/me")
    suspend fun checkAuthStatus():UserDto
    @POST()
    suspend fun logout (): Response<Unit>
}
