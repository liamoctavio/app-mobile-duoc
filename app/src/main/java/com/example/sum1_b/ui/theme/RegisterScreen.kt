package com.example.sum1_b.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.ui.graphics.Color
import com.example.sum1_b.viewmodel.AppViewModel
import java.util.*



import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

import android.widget.Toast

import androidx.compose.ui.platform.LocalContext
import android.Manifest
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.core.app.ActivityCompat







@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    viewModel: AppViewModel,
    context: Context
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val selectedMode by viewModel.inputMode.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    val speechRecognizerUsername = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val speechRecognizerEmail = remember { SpeechRecognizer.createSpeechRecognizer(context) }


    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown()
            speechRecognizer.destroy()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),

                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Registro",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    InputSelection(viewModel)
                    Spacer(modifier = Modifier.height(16.dp))


                    if (selectedMode == "Escribir") {
                        StyleTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = "Nombre de Usuario"
                        )
                    } else {
                        VoiceInputField(
                            label = "Nombre de Usuario",
                            value = username,
                            onValueChange = { username = it },
                            speechRecognizer = speechRecognizerUsername
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedMode == "Escribir") {
                        StyleTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Correo Electrónico"
                        )
                    } else {
                        VoiceInputField(
                            label = "Correo Electrónico",
                            value = email,
                            onValueChange = { email = it },
                            speechRecognizer = speechRecognizerEmail
                        )
                    }

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

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    speechRecognizer: SpeechRecognizer
) {


    val context = LocalContext.current
    var isListening by remember { mutableStateOf(false) }

    val recognitionListener = object : RecognitionListener {
        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!matches.isNullOrEmpty()) {
                onValueChange(matches[0])
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

    LaunchedEffect(Unit) {
        speechRecognizer.setRecognitionListener(recognitionListener)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent), // 🔹 Fondo transparente
                colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedIndicatorColor = Color.Black,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f)


            )
        )

        IconButton(
            onClick = {
                val activity = context as? Activity ?: return@IconButton

                if (!isListening) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.RECORD_AUDIO),
                            1002
                        )
                    } else {
                        isListening = true
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES") // 🔹 Forzar español
                            putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...") // 🔹 Mensaje de usuario
                        }
                        speechRecognizer.startListening(intent)
                    }
                }
            }
        ) {
            Icon(Icons.Default.Mic, contentDescription = "Hablar")
        }
    }
}


@Composable
fun InputSelection(viewModel: AppViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val selectedMode by viewModel.inputMode.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Seleccione método de entrada", style = MaterialTheme.typography.headlineSmall)

        Box {
            Button(onClick = { expanded = true }) {
                Text(selectedMode)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Escribir") },
                    onClick = {
                        viewModel.setInputMode("Escribir")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Hablar") },
                    onClick = {
                        viewModel.setInputMode("Hablar")
                        expanded = false
                    }
                )
            }
        }
    }
}