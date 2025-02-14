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

import com.example.sum1_b.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserViewModelTest {

    private lateinit var userViewModel: UserViewModel

    @Before
    fun setup() {
        // Inicializa tu ViewModel (aquí se podría configurar un estado inicial si lo necesitas)
        userViewModel = UserViewModel()
    }

    @Test
    fun testRegisterUser_Success() = runBlocking {
        // Se espera que al registrar un usuario nuevo no se retorne error (null indica éxito)
        val result = userViewModel.registerUser("TestUser", "test@example.com", "password")
        // En este ejemplo, asumimos que un resultado null significa que se registró exitosamente.
        assertNull("El registro debería ser exitoso y no retornar error", result)
    }



//    @Test
//    fun testRegisterUser_Failure() {
//        // Registra un usuario
//        userViewModel.registerUser("Juan", "juan@example.com", "password123")
//        // Intenta registrar otro usuario con el mismo correo
//        val result = userViewModel.registerUser("Juan2", "juan@example.com", "password456")
//        assertFalse("No se debe permitir registrar un usuario con correo duplicado", result)
//    }
//
//    @Test
//    fun testValidateUser() {
//        // Registra un usuario y verifica que la validación funcione
//        userViewModel.registerUser("Octavio", "octavio1@gmail.com", "octavio")
//        val isValid = userViewModel.validateUser("octavio1@gmail.com", "octavio")
//        assertTrue("El usuario debe validarse correctamente", isValid)
//    }
}
