package com.example.sum1_b.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.sum1_b.model.User

class UserViewModel : ViewModel() {
    private val _users = mutableStateListOf<User>(
        User(username = "octavio1", email = "octavio1@gmail.com", password = "octavio"),
        User(username = "octavio2", email = "octavio2@gmail.com", password = "octavio"),
        User(username = "octavio3", email = "octavio3@gmail.com", password = "octavio")
    )
    val users: List<User> get() = _users

    fun registerUser(username: String, email: String, password: String): Boolean {
        if (_users.any { it.email == email }) {
            return false
        }
        _users.add(User(username, email, password))
        return true
    }

    fun validateUser(email: String, password: String): Boolean {
        return _users.any { it.email == email && it.password == password }
    }
}