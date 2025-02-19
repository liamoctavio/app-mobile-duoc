package com.example.sum1_b

import android.app.Activity
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

import androidx.compose.ui.platform.LocalContext
import com.example.sum1_b.viewmodel.AppViewModel

import com.google.firebase.auth.FirebaseAuth
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import com.example.sum1_b.ui.theme.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions(this)

        enableEdgeToEdge()
        setContent {
            Sum1_bTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()
                val appViewModel: AppViewModel = viewModel()

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
                                onNavigateToRegister = {
                                    navController.navigate("register_screen")
                                },
                                onNavigateToHome = { userId ->
                                    navController.navigate("home_screen/$userId") {
                                        popUpTo("login_screen") { inclusive = true }
                                    }
                                },
                                onNavigateToRecover = {
                                    navController.navigate("recover_screen")
                                },
                                userViewModel = userViewModel,
                                context = LocalContext.current
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
                            val context = LocalContext.current
                            RegisterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                userViewModel = userViewModel,
                                viewModel = appViewModel,
                                context = context
                            )
                        }


                        composable("home_screen/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""

                            if (userId.isNotBlank()) {
                                HomeScreen(
                                    userId = userId,
                                    navController = navController,
                                    onLogout = {
                                        FirebaseAuth.getInstance().signOut()
                                        navController.navigate("login_screen") {
                                            popUpTo("home_screen") { inclusive = true }
                                        }
                                    }
                                )
                            } else {
                                navController.navigate("login_screen") {
                                    popUpTo("home_screen") { inclusive = true }
                                }
                            }
                        }

                        composable("profile_screen/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""

                            if (userId.isNotBlank()) {
                                ProfileScreen(userId = userId)
                            } else {
                                navController.navigate("home_screen/$userId") {
                                    popUpTo("profile_screen") { inclusive = true }
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}



private fun checkAndRequestPermissions(activity: Activity) {
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }) {

    } else {
        ActivityCompat.requestPermissions(activity, permissions, 1001)
    }
}


