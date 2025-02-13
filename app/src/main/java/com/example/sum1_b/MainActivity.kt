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
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

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
                                onNavigateToRegister = {
                                    navController.navigate("register_screen") // ⚠️ Navega a la pantalla de registro
                                },
                                onNavigateToHome = { userId -> // ✅ Recibe el userId y navega a HomeScreen
                                    navController.navigate("home_screen/$userId") {
                                        popUpTo("login_screen") { inclusive = true }
                                    }
                                },
                                onNavigateToRecover = {
                                    navController.navigate("recover_screen") // ⚠️ Si tienes recuperación de contraseña
                                },
                                userViewModel = userViewModel,
                                context = LocalContext.current
                            )
                        }
//                        composable("login_screen") {
//                            val context = LocalContext.current // Obtén el contexto actual
//                            LoginScreen(
//                                onNavigateToRecover = {
//                                    // Navegamos a la pantalla de recuperar
//                                    navController.navigate("recover_screen")
//                                },
//                                onNavigateToRegister = {
//                                    // Navegamos a la pantalla de registro
//                                    navController.navigate("register_screen")
//                                },
//                                onNavigateToHome = {
//                                    navController.navigate("home_screen") {
//                                        popUpTo("login_screen") { inclusive = true }
//                                    }
//                                },
//                                userViewModel = userViewModel,
//                                context = context // Pasa el contexto aquí
//
//                            )
//                        }

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
                            val context = LocalContext.current // Obtén el contexto actual
                            RegisterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                userViewModel = userViewModel,
                                context = context // Pasa el contexto aquí

                            )
                        }
                        // Pantalla Home
                        composable("home_screen/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""

                            if (userId.isNotBlank()) {
                                HomeScreen(
                                    userId = userId,
                                    onLogout = {
                                        FirebaseAuth.getInstance().signOut() // ⚠️ Cierra sesión en Firebase
                                        navController.navigate("login_screen") {
                                            popUpTo("home_screen") { inclusive = true }
                                        }
                                    }
                                )
                            } else {
                                // ⚠️ Si no hay userId, vuelve al login
                                navController.navigate("login_screen") {
                                    popUpTo("home_screen") { inclusive = true }
                                }
                            }
                        }
//                        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//                        composable("home_screen"){
//                            if (userId.isNotBlank()) {
//                                HomeScreen(
//                                    userId = userId,  // ✅ Pasar el userId de Firebase
//                                    onLogout = {
//                                        FirebaseAuth.getInstance().signOut() // Cierra sesión en Firebase
//                                        navController.navigate("login_screen") {
//                                            popUpTo("home_screen") { inclusive = true }
//                                        }
//                                    }
//                                )
//                            } else {
//                                // Si no hay usuario autenticado, volver a login
//                                navController.navigate("login_screen") {
//                                    popUpTo("home_screen") { inclusive = true }
//                                }
//                            }
//                        } //aa
                    }
                }
            }
        }
    }
}

