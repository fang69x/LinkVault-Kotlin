package com.fang.linkvault.domain.usecase.auth

import com.fang.linkvault.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email:String,password:String)= repository.login(email,password)
}
