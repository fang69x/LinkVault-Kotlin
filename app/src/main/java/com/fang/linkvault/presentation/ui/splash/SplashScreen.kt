package com.fang.linkvault.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    onNavigateToHome:() ->Unit,
    onNavigateToAuth:() ->Unit,
    viewModel: SplashViewModel= hiltViewModel()
){
    LaunchedEffect(true) {
        viewModel.events.collect { event->
            when(event){
                is SplashViewModel.UiEvent.NavigateToHome->onNavigateToHome
                is SplashViewModel.UiEvent.NavigateToAuth -> onNavigateToAuth
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5302C4)),
        contentAlignment = Alignment.Center
    ) {
        Column(){
            Text("This is the splash screen")
        }
    }

}