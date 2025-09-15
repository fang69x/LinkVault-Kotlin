package com.fang.linkvault.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yourusername.linkvault.presentation.ui.home.HomeScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home_screen"
    ) {
        composable(route = "home_screen") {
            HomeScreen(
                onNavigateToCreateBookmark = {},
                onNavigateToBookmarkDetail = {},
                viewModel = hiltViewModel()
            )

        }


    }
}