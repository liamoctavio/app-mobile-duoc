package com.example.sum1_b

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sum1_b.ui.theme.LoginScreen
import com.example.sum1_b.ui.theme.RecoverPasswordScreen
import com.example.sum1_b.ui.theme.RegisterScreen
import com.example.sum1_b.viewmodel.UserViewModel
import com.example.sum1_b.ui.theme.HomeScreen
import com.example.sum1_b.ui.theme.Sum1_bTheme
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sum1_bTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login_screen") {
                            LoginScreen(
                                onNavigateToRecover = {
                                    navController.navigate("recover_screen")
                                },
                                onNavigateToRegister = {
                                    navController.navigate("register_screen")
                                },
                                onNavigateToHome = {
                                    navController.navigate("home_screen") {
                                        popUpTo("login_screen") { inclusive = true }
                                    }
                                },
                                userViewModel = userViewModel
                            )
                        }

                        composable("recover_screen") {
                            RecoverPasswordScreen(
                                onRecoveryRequested = { email ->
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("register_screen") {
                            RegisterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                userViewModel = userViewModel
                            )
                        }
                        composable("home_screen"){
                            HomeScreen(
                                onLogout = {
                                    navController.navigate("login_screen"){
                                        popUpTo("home_screen"){ inclusive = true}
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

