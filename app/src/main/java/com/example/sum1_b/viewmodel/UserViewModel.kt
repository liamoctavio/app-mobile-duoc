package com.example.sum1_b.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.sum1_b.model.User

class UserViewModel : ViewModel() {
    // Lista mutable de usuarios observada por Compose
    private val _users = mutableStateListOf<User>(
        // Usuarios pre-creados para pruebas
        User(username = "octavio1", email = "octavio1@gmail.com", password = "octavio"),
        User(username = "octavio2", email = "octavio2@gmail.com", password = "octavio"),
        User(username = "octavio3", email = "octavio3@gmail.com", password = "octavio")
    )
    val users: List<User> get() = _users

    // Función para registrar un nuevo usuario
    fun registerUser(username: String, email: String, password: String): Boolean {
        // Verificar si el email ya está registrado
        if (_users.any { it.email == email }) {
            return false // Email ya existe
        }
        // Agregar el nuevo usuario
        _users.add(User(username, email, password))
        return true
    }

    // Función para validar el login
    fun validateUser(email: String, password: String): Boolean {
        return _users.any { it.email == email && it.password == password }
    }
}