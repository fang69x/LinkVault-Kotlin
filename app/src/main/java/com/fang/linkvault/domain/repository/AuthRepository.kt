package com.fang.linkvault.domain.repository

import com.fang.linkvault.data.dto.auth.RegisterResponseDto
import com.fang.linkvault.domain.model.User
import retrofit2.Response

interface AuthRepository {
    suspend fun login(email:String,password:String): Result<User>
    suspend fun register(name:String,email:String,password:String,avatarUrl:String?): Result<RegisterResponseDto>
    suspend fun checkAuthStatus(): Result<User>
    suspend fun logout():Result<Unit>
}