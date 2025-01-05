package com.example.sum1_b.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sum1_b.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRecover: () -> Unit, // callback para navegar a Recuperar Contraseña
    onNavigateToRegister: () -> Unit, // callback para navegar a Registro
    onNavigateToHome: () -> Unit, // callback para navegar a HomeScreen
    userViewModel: UserViewModel = viewModel()
) {
    // Variables de estado para los campos de usuario y contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Estado para mostrar mensajes de error
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Snackbar para retroalimentación
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Campo de texto para el email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = if (showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword)
                                Icons.Default.VisibilityOff
                            else
                                Icons.Default.Visibility,
                            contentDescription = if (showPassword)
                                "Ocultar contraseña"
                            else
                                "Mostrar contraseña"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botón de iniciar sesión
            Button(
                onClick = {
                    // Validaciones básicas
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Todos los campos son obligatorios."
                        return@Button
                    }
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Introduce un correo electrónico válido."
                        return@Button
                    }

                    // Validar credenciales
                    val valid = userViewModel.validateUser(email, password)
                    if (valid) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Login exitoso. ¡Bienvenido!")
                            onNavigateToHome()
                        }
                    } else {
                        errorMessage = "Credenciales inválidas. Inténtalo de nuevo."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }

            // Mostrar mensaje de error si existe
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Enlace a la pantalla de registro
            TextButton(onClick = { onNavigateToRegister() }) {
                Text("¿No tienes una cuenta? Regístrate")
            }

            // Enlace a la pantalla de recuperar contraseña
            TextButton(onClick = { onNavigateToRecover() }) {
                Text("¿Olvidaste tu contraseña?")
            }
        }
    }
}