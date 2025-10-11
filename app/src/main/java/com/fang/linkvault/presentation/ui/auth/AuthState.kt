package com.fang.linkvault.presentation.ui.auth

data class AuthState (
    val name:String ="",
    val email:String="",
    val password:String="",
    val isLoading:Boolean=false,
    val error :String?=null
)
