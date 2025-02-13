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


//@Composable
//fun HomeScreen(
//    userId: String, // Se debe pasar el ID del usuario autenticado
//    onLogout: () -> Unit,
//    userViewModel: UserViewModel = viewModel(),
//    modifier: Modifier = Modifier
//) {
////    var tasks by remember { mutableStateOf(listOf("Ejemplo: Comprar Leche")) }
////    var newTask by remember { mutableStateOf("") }
//    val tasks by userViewModel.tasks.collectAsState()
//    var newTask by remember { mutableStateOf("") }
//
//    // Cargar tareas desde Firebase al iniciar la pantalla
//    LaunchedEffect(Unit) {
//        userViewModel.getTasksFromFirebase(userId)
//    }
//
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.fondo4),
//            contentDescription = "Fondo de la pantalla principal",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                onClick = { /* Navegar a Perfil */ },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Perfil")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = onLogout,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.error
//                ),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Cerrar Sesión")
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//
//            TextField(
//                value = newTask,
//                onValueChange = { newTask = it },
//                label = { Text("Nueva Tarea") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    if (newTask.isNotBlank()) {
//                        userViewModel.saveTaskToFirebase(userId, newTask)
//                        newTask = ""
//                        userViewModel.getTasksFromFirebase(userId) // Recargar lista
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Agregar Tarea")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "Mis Tareas",
//                style = MaterialTheme.typography.headlineSmall,
//                color = MaterialTheme.colorScheme.onPrimary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            LazyColumn(
//                modifier = Modifier.fillMaxHeight()
//            ) {
//                items(tasks) { task ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = MaterialTheme.colorScheme.surface
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = task.description,
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                            IconButton(onClick = {
//                                userViewModel.deleteTaskFromFirebase(userId, task.id)
//                                userViewModel.getTasksFromFirebase(userId) // Recargar lista
//                            }) {
//                                Icon(
//                                    painter = painterResource(id = android.R.drawable.ic_delete),
//                                    contentDescription = "Eliminar Tarea"
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}



//@Composable
//fun HomeScreen(
//    userId: String, // Se debe pasar el ID del usuario autenticado
//    onLogout: () -> Unit,
//    userViewModel: UserViewModel = viewModel(),
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
//    val database = FirebaseDatabase.getInstance().reference.child("locations").child(userId)
//    val coroutineScope = rememberCoroutineScope()
//
//    var userLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
//    var mapView: MapView? by remember { mutableStateOf(null) }
//
//    val tasks by userViewModel.tasks.collectAsState()
//    var newTask by remember { mutableStateOf("") }
//
//    // Cargar tareas desde Firebase al iniciar la pantalla
////    LaunchedEffect(Unit) {
////        userViewModel.getTasksFromFirebase(userId)
////    }
//
//    LaunchedEffect(Unit) {
//        if (ActivityCompat.checkSelfPermission(
//                context, Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//                location?.let {
//                    val latLng = LatLng(it.latitude, it.longitude)
//                    userLocation = latLng
//                    database.setValue(mapOf("latitude" to it.latitude, "longitude" to it.longitude))
//                }
//            }
//        }
//    }
//
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.fondo4),
//            contentDescription = "Fondo de la pantalla principal",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                onClick = { /* Navegar a Perfil */ },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Perfil")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = onLogout,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.error
//                ),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Cerrar Sesión")
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // Input para nueva tarea
//            TextField(
//                value = newTask,
//                onValueChange = { newTask = it },
//                label = { Text("Nueva Tarea") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Botón para agregar tarea
//            Button(
//                onClick = {
//                    if (newTask.isNotBlank()) {
//                        userViewModel.saveTaskToFirebase(userId, newTask)
//                        newTask = ""
//                        userViewModel.getTasksFromFirebase(userId) // Recargar lista
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Agregar Tarea")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "Mis Tareas",
//                style = MaterialTheme.typography.headlineSmall,
//                color = MaterialTheme.colorScheme.onPrimary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Lista de tareas desde Firebase
//            LazyColumn(
//                modifier = Modifier.fillMaxHeight()
//            ) {
//                items(tasks) { task ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = MaterialTheme.colorScheme.surface
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = task.description,
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                            IconButton(onClick = {
//                                userViewModel.deleteTaskFromFirebase(userId, task.id)
//                                userViewModel.getTasksFromFirebase(userId) // Recargar lista
//                            }) {
//                                Icon(
//                                    painter = painterResource(id = android.R.drawable.ic_delete),
//                                    contentDescription = "Eliminar Tarea"
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}




//@SuppressLint("MissingPermission")
//@Composable
//fun HomeScreen(
//    userId: String,
//    onLogout: () -> Unit,
//    userViewModel: UserViewModel = viewModel(),
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
//    val database = FirebaseDatabase.getInstance().reference.child("locations").child(userId)
//
//    var userLocation by remember { mutableStateOf(GeoPoint(0.0, 0.0)) } // Cambio a GeoPoint (osmdroid)
//    var mapView: org.osmdroid.views.MapView? by remember { mutableStateOf(null) }
//    val activity = context as Activity // Necesario para solicitar permisos
//
//    val tasks by userViewModel.tasks.collectAsState()
//    var newTask by remember { mutableStateOf("") }
//
//    // Obtener ubicación del usuario y actualizar Firebase
//
//    LaunchedEffect(Unit) {
//        if (ActivityCompat.checkSelfPermission(
//                context, Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // 🚨 Si el permiso NO está concedido, solicitarlo al usuario
//            ActivityCompat.requestPermissions(
//                activity,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                1001
//            )
//        } else {
//            // ✅ Si el permiso está concedido, actualizar la ubicación en Firebase
//            val locationRequest = LocationRequest().apply {
//                interval = 5000
//                fastestInterval = 2000
//                priority = Priority.PRIORITY_HIGH_ACCURACY
//            }
//
//            val locationCallback = object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult) {
//                    locationResult.lastLocation?.let { location ->
//                        val geoPoint = GeoPoint(location.latitude, location.longitude)
//                        userLocation = geoPoint
//
//                        // 🔹 Guardar en Firebase la ubicación obtenida
//                        database.setValue(mapOf("latitude" to location.latitude, "longitude" to location.longitude))
//                    }
//                }
//            }
//
//            fusedLocationClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        }
//
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Botón de Perfil y Logout
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Button(onClick = { /* Navegar a Perfil */ }) {
//                Text("Perfil")
//            }
//            Button(
//                onClick = onLogout,
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//            ) {
//                Text("Cerrar Sesión")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Mapa con la ubicación del usuario usando osmdroid
//        AndroidView(
//            factory = { ctx ->
//                org.osmdroid.views.MapView(ctx).apply {
//                    setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
//                    setMultiTouchControls(true)
//                    isTilesScaledToDpi = true // Escalar tiles según la densidad de pantalla
//
//                    // 🔹 Configurar la caché para evitar bloqueos
//                    val osmConfig = org.osmdroid.config.Configuration.getInstance()
//                    osmConfig.userAgentValue = ctx.packageName
//                    osmConfig.cacheMapTileCount = 12 // Ajustar la cantidad de tiles en caché
//                    osmConfig.tileFileSystemCacheMaxBytes = 100000000L // 100MB de caché
//
//                    controller.setZoom(15.0)
//                    controller.setCenter(userLocation) // Centrar en la ubicación del usuario
//
//                    mapView = this
//
//                    // Escuchar cambios en Firebase
//                    database.addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val latitude = snapshot.child("latitude").value as? Double ?: return
//                            val longitude = snapshot.child("longitude").value as? Double ?: return
//                            val newLocation = GeoPoint(latitude, longitude)
//
//                            overlayManager.clear()
//                            val marker = org.osmdroid.views.overlay.Marker(this@apply).apply {
//                                position = newLocation
//                                setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM)
//                                title = "Mi Ubicación"
//                            }
//                            overlayManager.add(marker)
//                            controller.setCenter(newLocation)
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            println("❌ Error al obtener ubicación: ${error.message}")
//                        }
//                    })
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(250.dp) // Tamaño del mapa
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Input para nueva tarea
//        TextField(
//            value = newTask,
//            onValueChange = { newTask = it },
//            label = { Text("Nueva Tarea") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Botón para agregar tarea
//        Button(
//            onClick = {
//                if (newTask.isNotBlank()) {
//                    userViewModel.saveTaskToFirebase(userId, newTask)
//                    newTask = ""
//                    userViewModel.getTasksFromFirebase(userId) // Recargar lista
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Agregar Tarea")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Mis Tareas",
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.onPrimary
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Lista de tareas desde Firebase
//        LazyColumn(
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            items(tasks) { task ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = task.description,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        IconButton(onClick = {
//                            userViewModel.deleteTaskFromFirebase(userId, task.id)
//                            userViewModel.getTasksFromFirebase(userId) // Recargar lista
//                        }) {
//                            Icon(
//                                painter = painterResource(id = android.R.drawable.ic_delete),
//                                contentDescription = "Eliminar Tarea"
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//
//@SuppressLint("MissingPermission")
//@Composable
//fun HomeScreen(
//    userId: String,
//    onLogout: () -> Unit,
//    userViewModel: UserViewModel = viewModel(),
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
//    val database = FirebaseDatabase.getInstance().reference.child("locations").child(userId)
//    val activity = context as Activity
//
//    var locationText by remember { mutableStateOf("Ubicación no disponible") }
//
//    // 🔹 Obtener la ubicación actual y mostrar la ciudad/país
//    LaunchedEffect(Unit) {
//        if (ActivityCompat.checkSelfPermission(
//                context, Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                activity,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                1001
//            )
//        } else {
//            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//                location?.let {
//                    val geocoder = Geocoder(context, Locale.getDefault())
//                    try {
//                        val addressList = geocoder.getFromLocation(it.latitude, it.longitude, 1)
//                        if (!addressList.isNullOrEmpty()) {
//                            val address = addressList[0]
//                            locationText = "${address.locality}, ${address.countryName}"
//                        } else {
//                            locationText = "Ubicación no encontrada"
//                        }
//                    } catch (e: Exception) {
//                        locationText = "Error obteniendo ubicación"
//                    }
//                }
//            }
//        }
//    }
//
//    // 🔹 Mostrar ciudad y país en lugar del mapa
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Botón de Perfil y Logout
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Button(onClick = { /* Navegar a Perfil */ }) {
//                Text("Perfil")
//            }
//            Button(
//                onClick = onLogout,
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//            ) {
//                Text("Cerrar Sesión")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 🔹 Mostrar la ubicación en texto
//        Text(
//            text = "Ubicación: $locationText",
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.primary
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 🔹 Input para nueva tarea
//        var newTask by remember { mutableStateOf("") }
//        TextField(
//            value = newTask,
//            onValueChange = { newTask = it },
//            label = { Text("Nueva Tarea") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 🔹 Botón para agregar tarea
//        Button(
//            onClick = {
//                if (newTask.isNotBlank()) {
//                    userViewModel.saveTaskToFirebase(userId, newTask)
//                    newTask = ""
//                    userViewModel.getTasksFromFirebase(userId)
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Agregar Tarea")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Mis Tareas",
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.onPrimary
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // 🔹 Lista de tareas
//        val tasks by userViewModel.tasks.collectAsState()
//        LazyColumn(
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            items(tasks) { task ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = task.description,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        IconButton(onClick = {
//                            userViewModel.deleteTaskFromFirebase(userId, task.id)
//                            userViewModel.getTasksFromFirebase(userId)
//                        }) {
//                            Icon(
//                                painter = painterResource(id = android.R.drawable.ic_delete),
//                                contentDescription = "Eliminar Tarea"
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//
//@SuppressLint("MissingPermission")
//@Composable
//fun HomeScreen(
//    userId: String,
//    onLogout: () -> Unit,
//    userViewModel: UserViewModel = viewModel(),
//    modifier: Modifier = Modifier
//) {
//    val task by userViewModel.tasks.collectAsState()
//
//    val context = LocalContext.current
//    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
//    val database = FirebaseDatabase.getInstance().reference.child("locations").child(userId)
//    val activity = context as Activity
//
//    var locationText by remember { mutableStateOf("Ubicación no disponible") }
//
//    // 🔹 `LocationCallback` para recibir actualizaciones en tiempo real
//    val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            locationResult.lastLocation?.let { location ->
//                val geocoder = Geocoder(context, Locale.getDefault())
//                try {
//                    val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                    if (!addressList.isNullOrEmpty()) {
//                        val address = addressList[0]
//                        locationText = "${address.locality}, ${address.countryName}"
//
//                        // 🔹 Guardar en Firebase para persistencia
//                        database.setValue(mapOf("latitude" to location.latitude, "longitude" to location.longitude))
//                    } else {
//                        locationText = "Ubicación no encontrada"
//                    }
//                } catch (e: Exception) {
//                    locationText = "Error obteniendo ubicación"
//                }
//            }
//        }
//    }
//    // 🔹 Cargar tareas desde Firebase al abrir la pantalla (EFECTO SEPARADO)
//    LaunchedEffect(userId) {
//        userViewModel.getTasksFromFirebase(userId)
//    }
//
//    // 🔹 Obtener ubicación y actualizar en tiempo real
//    LaunchedEffect(Unit) {
//        if (ActivityCompat.checkSelfPermission(
//                context, Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                activity,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                1001
//            )
//        } else {
//            val locationRequest = LocationRequest().apply {
//                interval = 5000 // 📍 Actualización cada 5 segundos
//                fastestInterval = 2000
//                priority = Priority.PRIORITY_HIGH_ACCURACY
//            }
//
//            fusedLocationClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        }
//    }
//    Box(
//        modifier = modifier.fillMaxSize()
//    ){}
//
//    // 🔹 Mostrar ciudad y país en la UI
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Button(onClick = { /* Navegar a Perfil */ }) {
//                Text("Perfil")
//            }
//            Button(
//                onClick = onLogout,
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//            ) {
//                Text("Cerrar Sesión")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 🔹 Mostrar la ubicación en texto (actualizada en tiempo real)
//        Text(
//            text = "Ubicación: $locationText",
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.primary
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 🔹 Input para nueva tarea
//        var newTask by remember { mutableStateOf("") }
//        TextField(
//            value = newTask,
//            onValueChange = { newTask = it },
//            label = { Text("Nueva Tarea") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 🔹 Botón para agregar tarea
//        Button(
//            onClick = {
//                if (newTask.isNotBlank()) {
//                    userViewModel.saveTaskToFirebase(userId, newTask)
//                    newTask = ""
//                    userViewModel.getTasksFromFirebase(userId)
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Agregar Tarea")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Mis Tareas",
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.onPrimary
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // 🔹 Lista de tareas
//        val tasks by userViewModel.tasks.collectAsState()
//        LazyColumn(
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            items(tasks) { task ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = task.description,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        IconButton(onClick = {
//                            userViewModel.deleteTaskFromFirebase(userId, task.id)
//                            userViewModel.getTasksFromFirebase(userId)
//                        }) {
//                            Icon(
//                                painter = painterResource(id = android.R.drawable.ic_delete),
//                                contentDescription = "Eliminar Tarea"
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

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
