package com.fang.linkvault.data.dto.auth

data class LoginResponseDto(
    val token:String,
    val user:UserDto
)