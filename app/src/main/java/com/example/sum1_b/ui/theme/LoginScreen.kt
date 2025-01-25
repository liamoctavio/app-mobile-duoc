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
import android.speech.tts.TextToSpeech
import android.content.Context
import java.util.*

@Composable
fun LoginScreen(
    onNavigateToRecover: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    context: Context
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    var tts: TextToSpeech? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
        onDispose {
            tts?.shutdown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        val sinusar = paddingValues
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo),
                contentDescription = "Fondo temático violeta",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

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

                StyleTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo Electrónico",
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Icono de correo")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

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

                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Todos los campos son obligatorios."
                            tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                            return@Button
                        }
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            errorMessage = "Introduce un correo electrónico válido."
                            tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                            return@Button
                        }

                        val valid = userViewModel.validateUser(email, password)
                        if (valid) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Login exitoso. ¡Bienvenido!")
                                tts?.speak("Login exitoso. Bienvenido", TextToSpeech.QUEUE_FLUSH, null, null)
                                onNavigateToHome()
                            }
                        } else {
                            errorMessage = "Credenciales inválidas. Inténtalo de nuevo."
                            tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(onClick = { onNavigateToRegister() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                    ) {
                    Text("¿No tienes una cuenta? Regístrate")
                }

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

    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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


