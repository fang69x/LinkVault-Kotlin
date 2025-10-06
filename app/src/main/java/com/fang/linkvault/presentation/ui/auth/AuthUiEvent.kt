package com.fang.linkvault.presentation.ui.auth

sealed class AuthUiEvent {
    data object NavigateToHome: AuthUiEvent()
}