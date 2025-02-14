package com.example.sum1_b.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.sum1_b.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sum1_b.R
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import android.speech.tts.TextToSpeech
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import java.util.*


@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    context: Context
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(key1 = Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = "Fondo temático violeta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 30f) //
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clickable {
                            val textToSpeak = "Formulario de registro seleccionado. Los campos son: Nombre de Usuario, Correo Electrónico, Contraseña y Confirmar Contraseña."
                            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
                        },
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    Text(
                        text = "Registro",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StyleTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Nombre de Usuario"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StyleTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo Electrónico"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StyleTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    StyleTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirmar Contraseña",
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                                errorMessage = "Todos los campos son obligatorios."
                                tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                                return@Button
                            }
                            if (password != confirmPassword) {
                                errorMessage = "Las contraseñas no coinciden."
                                tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                                return@Button
                            }
                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                errorMessage = "Introduce un correo electrónico válido."
                                tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                                return@Button
                            }
                            // Activa la pantalla de carga
                            isLoading = true
                            coroutineScope.launch {
                                val error = userViewModel.registerUser(username, email, password)
                                if (error == null) {
                                    snackbarHostState.showSnackbar("Registro exitoso. ¡Bienvenido $username!")
                                    tts?.speak("Registro Exitoso", TextToSpeech.QUEUE_FLUSH, null, null)
                                    onNavigateBack()
                                } else {
                                    errorMessage = error
                                    tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                                }
                                isLoading = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Registrarse")
                    }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
