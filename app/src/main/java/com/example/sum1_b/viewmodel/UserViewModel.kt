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

    fun deleteTaskFromFirebase(userId: String, taskId: String) {
        database.child("usuarios").child(userId).child("tareas").child(taskId).removeValue()
            .addOnSuccessListener {
                println("✅ Tarea eliminada de Firebase.")
            }
            .addOnFailureListener { e ->
                println("❌ Error al eliminar tarea: ${e.message}")
            }
    }

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

    suspend fun loginUser(email: String, password: String): Boolean {
            return try {
                auth.signInWithEmailAndPassword(email, password).await()
                auth.currentUser != null
            } catch (e: Exception) {
                println("❌ Error al iniciar sesión: ${e.message}")
                false
            }
    }


    suspend fun registerUser(username: String, email: String, password: String): String? {
        return try {
            val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return "Error al obtener el ID de usuario"

            val user = User(username, email, password)
            database.child("usuarios").child(userId).setValue(user).await()
            null
        }catch (e: FirebaseAuthUserCollisionException) {
            "El correo electrónico ya está registrado."
        } catch (e: Exception) {
            "Error al registrar usuario: ${e.message}"
        }
    }

}