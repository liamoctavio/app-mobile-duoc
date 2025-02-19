package com.example.sum1_b.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sum1_b.R
import com.google.firebase.database.*

@Composable
fun ProfileScreen(userId: String) {
    val database = FirebaseDatabase.getInstance().reference.child("usuarios").child(userId)

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        database.get()
            .addOnSuccessListener { snapshot ->
                username = snapshot.child("username").value?.toString() ?: "Usuario Desconocido"
                email = snapshot.child("email").value?.toString() ?: "Correo No Disponible"
                isLoading = false
            }
            .addOnFailureListener { exception ->
                errorMessage = "Error al cargar datos: ${exception.message}"
                isLoading = false
            }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo4),
            contentDescription = "Fondo de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            } else {
                if (errorMessage != null) {
                    Text(text = errorMessage ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Perfil de Usuario",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Nombre: $username", style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Correo: $email", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

