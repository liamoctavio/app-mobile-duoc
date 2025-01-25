package com.example.sum1_b.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.sum1_b.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRecover: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
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
        val sinusar = paddingValues
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.fondo), // Cambia a tu imagen de fondo
                contentDescription = "Fondo temático violeta",
                contentScale = ContentScale.Crop, // Ajusta la imagen para cubrir todo el espacio
                modifier = Modifier.fillMaxSize()
            )

            // Contenido del login
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Logo de la app",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Campo de texto para el email
                StyleTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo Electrónico",
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Icono de correo")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la contraseña
                StyleTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Icono de candado")
                    },
                    isPassword = true,
                    showPassword = showPassword,
                    onPasswordToggle = { showPassword = !showPassword }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de iniciar sesión
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
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
                TextButton(onClick = { onNavigateToRegister() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                    ) {
                    Text("¿No tienes una cuenta? Regístrate")
                }

                // Enlace a la pantalla de recuperar contraseña
                TextButton(onClick = { onNavigateToRecover() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                    ) {
                    Text("¿Olvidaste tu contraseña?")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class) // Indica que aceptas usar esta API experimental
@Composable
fun StyleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = leadingIcon,
        singleLine = true,
        visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword && onPasswordToggle != null) {
            {
                IconButton(onClick = onPasswordToggle) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        } else null,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            focusedLeadingIconColor  = MaterialTheme.colorScheme.onBackground,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}


