package com.fang.linkvault.domain.usecase.auth

import com.fang.linkvault.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository : AuthRepository
) {
    suspend operator fun invoke(name:String, email:String, password:String)= repository.register(name,email,password, avatarUrl = " ")
}