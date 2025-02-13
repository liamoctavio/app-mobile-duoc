package com.example.sum1_b.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.sum1_b.model.User
import com.google.firebase.database.*

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
//import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



data class User(val username: String, val email: String, val password: String)
data class Task(val id: String, val description: String)

class UserViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks


    // Guardar una tarea en Firebase
    fun saveTaskToFirebase(userId: String, taskDescription: String) {
        val taskId = database.child("usuarios").child(userId).child("tareas").push().key ?: return
        val task = Task(taskId, taskDescription)

        database.child("usuarios").child(userId).child("tareas").child(taskId).setValue(task)
            .addOnSuccessListener {
                println("✅ Tarea guardada en Firebase.")
            }
            .addOnFailureListener { e ->
                println("❌ Error al guardar tarea: ${e.message}")
            }
    }

    // Cargar las tareas desde Firebase
    fun getTasksFromFirebase(userId: String) {
        viewModelScope.launch {
            try {
                val snapshot = database.child("usuarios").child(userId).child("tareas").get().await()
                val taskList = mutableListOf<Task>()
                for (taskSnapshot in snapshot.children) {
                    val id = taskSnapshot.child("id").value.toString()
                    val description = taskSnapshot.child("description").value.toString()
                    taskList.add(Task(id, description))
                }
                _tasks.value = taskList
            } catch (e: Exception) {
                println("❌ Error al obtener tareas: ${e.message}")
            }
        }
    }

    // Eliminar una tarea de Firebase
    fun deleteTaskFromFirebase(userId: String, taskId: String) {
        database.child("usuarios").child(userId).child("tareas").child(taskId).removeValue()
            .addOnSuccessListener {
                println("✅ Tarea eliminada de Firebase.")
            }
            .addOnFailureListener { e ->
                println("❌ Error al eliminar tarea: ${e.message}")
            }
    }

    // Método para guardar usuario en Firebase
    fun saveUserToFirebase(userId: String, username: String, email: String, password: String) {
        val user = User(username, email, password)
        database.child("usuarios").child(userId).setValue(user)
            .addOnSuccessListener {
                println("✅ Usuario guardado en Firebase correctamente.")
            }
            .addOnFailureListener { e ->
                println("❌ Error al guardar usuario en Firebase: ${e.message}")
            }
    }

//    // Método para leer usuario desde Firebase
        suspend fun loginUser(email: String, password: String): Boolean {
            return try {
                auth.signInWithEmailAndPassword(email, password).await()
                auth.currentUser != null // ✅ Retorna true si el usuario se autenticó correctamente
            } catch (e: Exception) {
                println("❌ Error al iniciar sesión: ${e.message}")
                false
            }
        }
//    suspend fun loginUser(email: String, password: String): Boolean {
//        return try {
//            val snapshot = database.child("usuarios").get().await()
//            for (userSnapshot in snapshot.children) {
//                val storedEmail = userSnapshot.child("email").value.toString()
//                val storedPassword = userSnapshot.child("password").value.toString()
//
//                if (storedEmail == email && storedPassword == password) {
//                    return true // Usuario encontrado con credenciales correctas
//                }
//            }
//            false
//        } catch (e: Exception) {
//            println("❌ Error al leer usuario desde Firebase: ${e.message}")
//            false
//        }
//    }

    // Método para registrar usuario en Firebase
    suspend fun registerUser(username: String, email: String, password: String): String? {
        return try {
            // 🔹 Registra el usuario en Firebase Authentication
            val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return "Error al obtener el ID de usuario"  // Obtiene el userId generado por Firebase Authentication

            // 🔹 Guarda los datos del usuario en Firebase Realtime Database
            val user = User(username, email, password)
            database.child("usuarios").child(userId).setValue(user).await()
            null // ✅ Registro exitoso
        }catch (e: FirebaseAuthUserCollisionException) {
            "El correo electrónico ya está registrado." // ⚠️ Mensaje específico para correos duplicados
        } catch (e: Exception) {
            "Error al registrar usuario: ${e.message}" // ⚠️ Muestra otros errores
        }
    }
//    suspend fun registerUser(username: String, email: String, password: String): Boolean {
//        return try {
//            // Verificar si el usuario ya existe en la base de datos
//            val snapshot = database.child("usuarios").get().await()
//            for (userSnapshot in snapshot.children) {
//                val storedEmail = userSnapshot.child("email").value.toString()
//                if (storedEmail == email) {
//                    return false // El usuario ya está registrado
//                }
//            }
//
//            // Si el usuario no existe, guardarlo en Firebase
//            val userId = database.child("usuarios").push().key ?: return false
//            val user = User(username, email, password)
//            database.child("usuarios").child(userId).setValue(user).await()
//            true // Registro exitoso
//        } catch (e: Exception) {
//            println("❌ Error al registrar usuario: ${e.message}")
//            false
//        }
//    }
}