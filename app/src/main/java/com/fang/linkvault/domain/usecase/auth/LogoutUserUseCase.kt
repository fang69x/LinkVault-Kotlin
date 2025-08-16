package com.fang.linkvault.domain.usecase.auth

import com.fang.linkvault.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() =repository.checkAuthStatus()
}
