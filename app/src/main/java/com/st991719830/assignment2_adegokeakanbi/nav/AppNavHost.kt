package com.st991719830.assignment2_adegokeakanbi.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.st991719830.assignment2_adegokeakanbi.ui.FeedScreen
import com.st991719830.assignment2_adegokeakanbi.ui.LoginScreen
import com.st991719830.assignment2_adegokeakanbi.ui.ProfileScreen
import com.st991719830.assignment2_adegokeakanbi.ui.SignupScreen
import com.st991719830.assignment2_adegokeakanbi.viewmodel.AuthViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: AuthViewModel) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, viewModel) }
        composable("signup") { SignupScreen(navController, viewModel) }
        composable("feed") { FeedScreen(navController, viewModel) }
        composable("profile") { ProfileScreen(navController, viewModel) }
    }
}
