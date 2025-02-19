package com.example.sum1_b.ui.theme

import android.app.Activity
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
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Mic
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import java.util.*


@Composable
fun LoginScreen(
    onNavigateToRecover: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: (String) -> Unit,
    userViewModel: UserViewModel = viewModel(),
    context: Context
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var tts: TextToSpeech? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
        onDispose { tts?.shutdown() }
    }

    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var isListening by remember { mutableStateOf(false) }
    val recognitionListener = object : RecognitionListener {
        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!matches.isNullOrEmpty()) {
                email = matches[0]
            }
            isListening = false
        }
        override fun onError(error: Int) {
            Toast.makeText(context, "Error de reconocimiento: $error", Toast.LENGTH_SHORT).show()
            isListening = false
        }
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }
    LaunchedEffect(Unit) { speechRecognizer.setRecognitionListener(recognitionListener) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StyleTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo Electrónico",
                            leadingIcon = {
                                Icon(Icons.Default.Email, contentDescription = "Icono de correo")
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        if (!isListening) {
                                            if (ActivityCompat.checkSelfPermission(
                                                    context, android.Manifest.permission.RECORD_AUDIO
                                                ) != PackageManager.PERMISSION_GRANTED
                                            ) {
                                                ActivityCompat.requestPermissions(
                                                    context as Activity,
                                                    arrayOf(android.Manifest.permission.RECORD_AUDIO),
                                                    1002
                                                )
                                            } else {
                                                isListening = true
                                                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                                    putExtra(
                                                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                                    )
                                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
                                                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Dicta tu correo")
                                                }
                                                speechRecognizer.startListening(intent)
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mic,
                                        contentDescription = "Dictar correo",
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                    }
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

                            isLoading = true
                            coroutineScope.launch {
                                val valid = userViewModel.loginUser(email, password)
                                if (valid) {
                                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                                    if (userId.isNotBlank()) {
                                        val userName = userViewModel.getUserNameFromFirebase(userId)

                                        val welcomeMessage = "¡Bienvenido $userName!"
                                        snackbarHostState.showSnackbar(welcomeMessage)
                                        tts?.speak(welcomeMessage, TextToSpeech.QUEUE_FLUSH, null, null)


                                        onNavigateToHome(userId)
                                    } else {
                                        errorMessage = "Error al obtener usuario. Inténtalo de nuevo."
                                        tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                                    }
                                } else {
                                    errorMessage = "Credenciales inválidas. Inténtalo de nuevo."
                                    tts?.speak(errorMessage, TextToSpeech.QUEUE_FLUSH, null, null)
                                }
                                isLoading = false
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

                    TextButton(
                        onClick = { onNavigateToRegister() },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text("¿No tienes una cuenta? Regístrate")
                    }

                    TextButton(
                        onClick = { onNavigateToRecover() },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text("¿Olvidaste tu contraseña?")
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

    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown()
            speechRecognizer.destroy()
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
    trailingIcon: @Composable (() -> Unit)? = null,
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
        trailingIcon = trailingIcon ?: if (isPassword && onPasswordToggle != null) {
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
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}



