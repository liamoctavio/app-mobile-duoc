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
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    context: Context
) {
    // Estados para los campos de texto
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Estado para mostrar mensajes de error
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Snackbar para retroalimentación
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Inicializar TextToSpeech
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(key1 = Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
    }

    // Liberar recursos de TTS al salir
    DisposableEffect(Unit) {
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
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.fondo), // Imagen de fondo
                contentDescription = "Fondo temático violeta",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Contenido del registro
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clickable {
                        val textToSpeak = "Formulario de registro seleccionado. " +
                            "Los campos son: Nombre de Usuario, Correo Electrónico, Contraseña y Confirmar Contraseña."
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
                        modifier = Modifier.padding(bottom = 16.dp) // Espacio para separar del resto
                    )
                }
                Text(
                    text = "Registro",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary // Texto blanco
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Nombre de Usuario
                StyleTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Nombre de Usuario"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Correo Electrónico
                StyleTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo Electrónico"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Contraseña
                StyleTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Confirmar Contraseña
                StyleTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirmar Contraseña",
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Registro
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

                        val success = userViewModel.registerUser(username, email, password)
                        if (success) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Registro exitoso. ¡Bienvenido $username!")
                                tts?.speak("Registro, Exitoso", TextToSpeech.QUEUE_FLUSH, null, null)
                                onNavigateBack()
                            }
                        } else {
                            errorMessage = "El correo electrónico ya está registrado."
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

                // Mostrar mensaje de error si existe
//                if (errorMessage != null) {
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Text(
//                        text = errorMessage ?: "",
//                        color = Color.White,
//                        style = MaterialTheme.typography.bodyMedium,
//                    )
//                }
            }
        }
    }
}
