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
import android.R.attr.textColor
import android.app.Activity
import android.location.LocationRequest
import android.os.Build
import android.os.Looper
import androidx.compose.ui.viewinterop.AndroidView




import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.model.MarkerOptions

import com.google.firebase.database.*
import org.osmdroid.util.GeoPoint
import com.google.android.gms.location.Priority

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.navigation.NavController
import com.google.android.gms.location.*
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(
    userId: String,
    navController: NavController, // ← Agregado
    onLogout: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val tasks by userViewModel.tasks.collectAsState()
    var locationText by remember { mutableStateOf("Ubicación no disponible") }

    var newTask by remember { mutableStateOf("") }
    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var isListening by remember { mutableStateOf(false) }

    val recognitionListener = object : RecognitionListener {
        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!matches.isNullOrEmpty()) {
                newTask = matches[0]
            }
            isListening = false
        }
        override fun onError(error: Int) {
            Toast.makeText(context, "Error de reconocimiento", Toast.LENGTH_SHORT).show()
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
    speechRecognizer.setRecognitionListener(recognitionListener)

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val database = FirebaseDatabase.getInstance().reference.child("locations").child(userId)
    val activity = context as Activity

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addressList.isNullOrEmpty()) {
                        val address = addressList[0]
                        locationText = "${address.locality}, ${address.countryName}"
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

    LaunchedEffect(userId) {
        userViewModel.getTasksFromFirebase(userId)
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1001
            )
        } else {
            val locationRequest = LocationRequest().apply {
                interval = 5000
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo4),
            contentDescription = "Fondo de la pantalla Home",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(onClick = { navController.navigate("profile_screen/$userId") }) {
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

            Text(
                text = "Ubicación: $locationText",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("Nueva Tarea") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)

                    )

                )
                IconButton(
                    onClick = {
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
//                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES") // Forzar español
                                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
                                }
                                speechRecognizer.startListening(intent)
                            }
                        }

                    }
                ) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "Hablar", tint = Color(0xFF6200EE) )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
