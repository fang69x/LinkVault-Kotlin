package com.fang.linkvault.presentation.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fang.linkvault.domain.repository.AuthRepository
import com.fang.linkvault.domain.usecase.auth.LoginUserUseCase
import com.fang.linkvault.domain.usecase.auth.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel(){
private val _state= MutableStateFlow(AuthState())
    val state= _state.asStateFlow()

    private val _eventChannel = Channel<AuthUiEvent>()
    val events= _eventChannel.receiveAsFlow()

    fun onNameChanged(name:String){
        _state.update {
            it.copy(name=name, error = null)
        }
    }
    fun onEmailChanged(email:String)
    {
        _state.update{it.copy(email=email,error=null)}
    }
    fun onPasswordChanged(passsword:String){
        _state.update { it.copy(password = passsword, error = null) }
    }

    fun onLoginClicked( ){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginUserUseCase(state.value.email,state.value.password)
                .onSuccess {
                    Log.d("onLoginClicked","is in onSuccess call")
_state.update { it.copy(isLoading = false) }
                    _eventChannel.send(AuthUiEvent.NavigateToHome)
                    Log.d("onLoginClicked","event channel has the message to navigate")

                }
                .onFailure { error->
                    Log.d("onLoginClicked","is in onError call")

                    _state.update { it.copy(isLoading = false, error = error.message) }

                }
        }
    }

    fun onRegisterClicked(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            registerUserUseCase("New User", state.value.email,state.value.password)
                .onSuccess {
                    onLoginClicked()
                }
                .onFailure { error->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}