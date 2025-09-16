package com.fang.linkvault.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fang.linkvault.domain.usecase.auth.CheckAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
private val checkAuthStatusUseCase: CheckAuthStatusUseCase
) : ViewModel(){
    sealed class UiEvent{
        data object NavigateToHome : UiEvent()
        data object NavigateToAuth : UiEvent()
    }
    private val _eventChannel = Channel <UiEvent>()
    val events =_eventChannel.receiveAsFlow()
    init {
        checkAuthStatus()
    }
    private fun checkAuthStatus(){
        viewModelScope.launch {
            delay(1500)
            checkAuthStatusUseCase()
                .onSuccess {
                    _eventChannel.send(UiEvent.NavigateToHome)
                }
                .onFailure {
                    _eventChannel.send(UiEvent.NavigateToAuth)
                }
        }
    }
}