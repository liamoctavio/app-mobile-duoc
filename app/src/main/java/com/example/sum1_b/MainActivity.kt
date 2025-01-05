package com.example.sum1_b

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
//import java.lang.reflect.Modifier
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
//import androidx.compose.material.Button
//import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.unit.dp

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
                        // Pantalla de Login
                        composable("login_screen") {
                            LoginScreen(
                                onNavigateToRecover = {
                                    // Navegamos a la pantalla de recuperar
                                    navController.navigate("recover_screen")
                                },
                                onNavigateToRegister = {
                                    // Navegamos a la pantalla de registro
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

                        // Pantalla de Recuperar Contraseña
                        composable("recover_screen") {
                            RecoverPasswordScreen(
                                onRecoveryRequested = { email ->
                                    // Aquí podrías manejar la lógica de recuperación
                                    // Por simplicidad, solo volvemos a la pantalla de login
                                    navController.popBackStack()
                                }
                            )
                        }

                        // Pantalla de Registro
                        composable("register_screen") {
                            RegisterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                userViewModel = userViewModel
                            )
                        }
                        // Pantalla Home
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

