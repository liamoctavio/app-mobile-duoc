package com.example.sum1_b

//import org.junit.Test
//
//import org.junit.Assert.*
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }
//}

//import android.content.Context
//import androidx.test.core.app.ApplicationProvider
//import com.example.sum1_b.viewmodel.UserViewModel
//import com.google.firebase.FirebaseApp
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertTrue
//import org.junit.Before
//import org.junit.Test
//
//
//class UserViewModelTest {
//
//    private lateinit var userViewModel: UserViewModel
//
//    @Before
//    fun setup() {
//        val appContext = ApplicationProvider.getApplicationContext<Context>()
//        // Inicializa Firebase en el contexto de la prueba
//        FirebaseApp.initializeApp(appContext)
//        userViewModel = UserViewModel()
//    }
//
//    @Test
//    fun testRegisterUser_Success() = runBlocking {
//        // Se espera que al registrar un usuario nuevo no se retorne error (null indica éxito)
//        val result = userViewModel.registerUser("TestUser", "test@example.com", "password")
//        // En este ejemplo, asumimos que un resultado null significa que se registró exitosamente.
//        assertNull("El registro debería ser exitoso y no retornar error", result)
//    }
//
//}


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.example.sum1_b.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNull
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

//    @Test
//    fun testRegisterUser_Success() = runBlocking {
//        // Se espera que al registrar un usuario nuevo no se retorne error (null indica éxito)
//        val result = userViewModel.registerUser("TestUser", "test@example.com", "password")
//        // En este ejemplo, asumimos que un resultado null significa que se registró exitosamente.
//        assertNull("El registro debería ser exitoso y no retornar error", result)
//    }
        @Test
        fun testRegisterUser_Success() = runBlocking {
            val result = userViewModel.registerUser("TestUser", "test@example.com", "password")
            assertNull("El registro debería ser exitoso y no retornar error", result)
        }
}

