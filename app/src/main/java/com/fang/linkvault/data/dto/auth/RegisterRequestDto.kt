package com.fang.linkvault.data.dto.auth

data class RegisterRequestDto (
    val email:String,
    val password:String,
    val name:String
)