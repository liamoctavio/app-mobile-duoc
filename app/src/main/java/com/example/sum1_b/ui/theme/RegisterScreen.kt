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

@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RegisterScreen(
//    onNavigateBack: () -> Unit,
//    userViewModel: UserViewModel = viewModel()
//) {
//    // Estados para los campos de texto
//    var username by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//
//    // Estado para mostrar mensajes de error
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    // Snackbar para retroalimentación
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//                .padding(paddingValues),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Registro",
//                style = MaterialTheme.typography.headlineLarge
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Campo de Username
//            OutlinedTextField(
//                value = username,
//                onValueChange = { username = it },
//                label = { Text("Nombre de Usuario") },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Campo de Email
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Correo Electrónico") },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Campo de Contraseña
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Contraseña") },
//                singleLine = true,
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Campo de Confirmar Contraseña
//            OutlinedTextField(
//                value = confirmPassword,
//                onValueChange = { confirmPassword = it },
//                label = { Text("Confirmar Contraseña") },
//                singleLine = true,
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Botón de Registro
//            Button(
//                onClick = {
//                    // Validaciones básicas
//                    if (username.isBlank() || email.isBlank() || password.isBlank()) {
//                        errorMessage = "Todos los campos son obligatorios."
//                        return@Button
//                    }
//                    if (password != confirmPassword) {
//                        errorMessage = "Las contraseñas no coinciden."
//                        return@Button
//                    }
//                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                        errorMessage = "Introduce un correo electrónico válido."
//                        return@Button
//                    }
//
//                    // Intentar registrar el usuario
//                    val success = userViewModel.registerUser(username, email, password)
//                    if (success) {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("Registro exitoso. ¡Bienvenido $username!")
//                            onNavigateBack()
//                        }
//                    } else {
//                        errorMessage = "El correo electrónico ya está registrado."
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Registrarse")
//            }
//
//            // Mostrar mensaje de error si existe
//            if (errorMessage != null) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = errorMessage ?: "",
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}


@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    userViewModel: UserViewModel = viewModel()
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

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registro",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary // Texto blanco
                )
                Spacer(modifier = Modifier.height(24.dp))

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
                Spacer(modifier = Modifier.height(24.dp))

                // Botón de Registro
                Button(
                    onClick = {
                        if (username.isBlank() || email.isBlank() || password.isBlank()) {
                            errorMessage = "Todos los campos son obligatorios."
                            return@Button
                        }
                        if (password != confirmPassword) {
                            errorMessage = "Las contraseñas no coinciden."
                            return@Button
                        }
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            errorMessage = "Introduce un correo electrónico válido."
                            return@Button
                        }

                        val success = userViewModel.registerUser(username, email, password)
                        if (success) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Registro exitoso. ¡Bienvenido $username!")
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
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
            }
        }
    }
}
