package com.example.sum1_b.ui.theme

import android.R.attr.value
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sum1_b.R


//
//
//@Composable
//fun HomeScreen(
//    onLogout: () -> Unit,
//    modifier: Modifier = Modifier
//
//) {
//    var tasks by remember { mutableStateOf(listOf<String>()) }
//    var newTask by remember { mutableStateOf("") }
//    Box(
//        modifier = modifier.fillMaxSize()
//    ){
//        Image(
//            painter = painterResource(id = R.drawable.fondo_home), // Cambia "fondo" por el nombre de tu imagen
//            contentDescription = "Fondo de la pantalla principal",
//            contentScale = ContentScale.Crop, // Ajusta la imagen para cubrir todo el espacio
//            modifier = Modifier.fillMaxSize()
//        )
//
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Top, // Mueve el contenido hacia arriba
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Opciones principales
//            Button(
//                onClick = { /* Navegar a Perfil */ },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Perfil")
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { /* Navegar a Configuración */ },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Configuración")
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Botón de cerrar sesión
//            Button(
//                onClick = onLogout,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.error
//                ),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Cerrar Sesión")
//            }
//        }
//    }
//}

//
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tasks by remember { mutableStateOf(listOf("Ejemplo: Comprar Leche")) }
    var newTask by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_home), // Cambia "fondo_home" por tu imagen
            contentDescription = "Fondo de la pantalla principal",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botones principales
            Button(
                onClick = { /* Navegar a Perfil */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campo para agregar tareas
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("Nueva Tarea") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        tasks = tasks + newTask
                        newTask = "" // Limpia el campo después de agregar
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Tarea")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de tareas
            Text(
                text = "Mis Tareas",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(tasks.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tasks[index],
                                style = MaterialTheme.typography.bodyMedium
                            )
                            IconButton(onClick = {
                                tasks = tasks.filterIndexed { i, _ -> i != index }
                            }) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_delete),
                                    contentDescription = "Eliminar Tarea"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}





