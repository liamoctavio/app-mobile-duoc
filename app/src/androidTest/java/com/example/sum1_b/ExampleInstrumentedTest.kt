package com.example.sum1_b

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.example.sum1_b.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserViewModelTest {

    private lateinit var userViewModel: UserViewModel

    @Before
    fun setup() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        if (FirebaseApp.getApps(appContext).isEmpty()) {
            FirebaseApp.initializeApp(appContext)
        }
        userViewModel = UserViewModel()
    }


    @Test
    fun testRegisterUser_Success() = runBlocking {
        val result = userViewModel.registerUser("TestUser", "test@example.com", "password")
        assertNull("El registro debería ser exitoso y no retornar error", result)
    }

    @Test
    fun testLoginUser_Failure() = runBlocking {
        val email = "wrong@example.com"
        val password = "wrongpassword"

        val result = userViewModel.loginUser(email, password)

        assertFalse("El login debería fallar y retornar false", result)
    }
    @Test
    fun testDeleteTaskFromFirebase() = runBlocking {
        val userId = "testUser123"

        userViewModel.saveTaskToFirebase(userId, "Tarea de prueba")

        delay(2000)

        userViewModel.getTasksFromFirebase(userId)
        delay(2000)

        val tasksBefore = userViewModel.tasks.value
        assertTrue("La lista de tareas no debería estar vacía", tasksBefore.isNotEmpty())

        val taskId = tasksBefore.first().id

        userViewModel.deleteTaskFromFirebase(userId, taskId)

        delay(2000)

        userViewModel.getTasksFromFirebase(userId)
        delay(2000)

        val tasksAfter = userViewModel.tasks.value
        assertTrue("La tarea debería haber sido eliminada", tasksAfter.none { it.id == taskId })
    }

    @Test
    fun testLoginUser_Success() = runBlocking {
        val email = "octavio12@gmail.com"
        val password = "123456"

        val result = userViewModel.loginUser(email, password)

        assertTrue("El inicio de sesión debería ser exitoso", result)
    }

    @Test
    fun testGetTasksFromFirebase() = runBlocking {
        val userId = "testUser123"

        userViewModel.saveTaskToFirebase(userId, "Tarea de prueba")

        delay(2000)

        userViewModel.getTasksFromFirebase(userId)
        delay(2000)

        val tasks = userViewModel.tasks.value
        assertTrue("Debería haber al menos una tarea almacenada", tasks.isNotEmpty())
    }




}