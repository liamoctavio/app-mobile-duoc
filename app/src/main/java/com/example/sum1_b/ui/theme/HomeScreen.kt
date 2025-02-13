package com.example.sum1_b.ui.theme

import android.R.attr.value
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sum1_b.R
import com.example.sum1_b.viewmodel.UserViewModel
import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.MapView
//import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import android.location.Location
import android.Manifest
import android.app.Activity
import android.location.LocationRequest
import android.os.Build
import android.os.Looper
import androidx.compose.ui.viewinterop.AndroidView


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.model.MarkerOptions

import com.google.firebase.database.*
import org.osmdroid.util.GeoPoint
import com.google.android.gms.location.Priority

import android.annotation.SuppressLint
import android.location.Geocoder
import com.google.android.gms.location.*
import java.util.Locale



@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(
    userId: String,
    onLogout: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val task by userViewModel.tasks.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val database = FirebaseDatabase.getInstance().reference.child("locations").child(userId)
    val activity = context as Activity

    var locationText by remember { mutableStateOf("Ubicación no disponible") }

    // 🔹 `LocationCallback` para recibir actualizaciones en tiempo real
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addressList.isNullOrEmpty()) {
                        val address = addressList[0]
                        locationText = "${address.locality}, ${address.countryName}"

                        // 🔹 Guardar en Firebase para persistencia
                        database.setValue(mapOf("latitude" to location.latitude, "longitude" to location.longitude))
                    } else {
                        locationText = "Ubicación no encontrada"
                    }
                } catch (e: Exception) {
                    locationText = "Error obteniendo ubicación"
                }
            }
        }
    }

    // 🔹 Cargar tareas desde Firebase al abrir la pantalla (EFECTO SEPARADO)
    LaunchedEffect(userId) {
        userViewModel.getTasksFromFirebase(userId)
    }

    // 🔹 Obtener ubicación y actualizar en tiempo real
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1001
            )
        } else {
            val locationRequest = LocationRequest().apply {
                interval = 5000 // 📍 Actualización cada 5 segundos
                fastestInterval = 2000
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    // 🔹 Envolver todo en un `Box` para agregar la imagen de fondo
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 🖼 Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo4), // 📌 Asegúrate de que la imagen está en res/drawable/
            contentDescription = "Fondo de la pantalla Home",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🔹 Contenido principal sobre la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /* Navegar a Perfil */ }) {
                    Text("Perfil")
                }
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cerrar Sesión")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Mostrar la ubicación en texto (actualizada en tiempo real)
            Text(
                text = "Ubicación: $locationText",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Input para nueva tarea
            var newTask by remember { mutableStateOf("") }
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("Nueva Tarea") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Botón para agregar tarea
            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        userViewModel.saveTaskToFirebase(userId, newTask)
                        newTask = ""
                        userViewModel.getTasksFromFirebase(userId)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Tarea")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mis Tareas",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Lista de tareas
            val tasks by userViewModel.tasks.collectAsState()
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = task.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            IconButton(onClick = {
                                userViewModel.deleteTaskFromFirebase(userId, task.id)
                                userViewModel.getTasksFromFirebase(userId)
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
