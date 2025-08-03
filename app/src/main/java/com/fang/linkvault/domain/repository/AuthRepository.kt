package com.fang.linkvault.domain.repository

import com.fang.linkvault.domain.model.User

interface AuthRepository {
    suspend fun login(email:String,password:String): Result<User>
    suspend fun register(name:String,email:String,password:String):Result<Unit>
    suspend fun checkAuthStatus(): Result<User>
    suspend fun logout():Result<Unit>
}