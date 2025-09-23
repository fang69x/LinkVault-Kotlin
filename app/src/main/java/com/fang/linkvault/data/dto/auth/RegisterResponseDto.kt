package com.fang.linkvault.data.dto.auth

import com.fang.linkvault.domain.model.User

data class RegisterResponseDto (
    val message: String,
    val token:String,
    val user: User
)
