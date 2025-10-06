package com.fang.linkvault.presentation.ui.auth

data class AuthState (
    val email:String="",
    val password:String="",
    val isLoadiing:Boolean=false,
    val error :String?=null
)
