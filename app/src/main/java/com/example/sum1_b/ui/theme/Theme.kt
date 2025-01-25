package com.example.sum1_b.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext



import androidx.compose.material3.lightColorScheme

import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFBA68C8)      // Amatista claro
val Secondary = Color(0xFFFF4081)    // Rosa brillante
val Background = Color.Transparent   // Fondo degradado ya visible
val Surface = Color(0xFF6A1B9A)      // Violeta intenso
val Error = Color(0xFFD32F2F)        // Rojo para errores, no es necesario cambiar
val OnPrimary = Color(0xFFFFFFFF)    // Blanco para contrastar con el Primary
val OnSecondary = Color(0xFFFFFFFF)  // Blanco para elementos secundarios
val OnBackground = Color(0xFFFFFFFF) // Blanco para elementos sobre el fondo
val OnSurface = Color(0xFFFFFFFF)    // Bl

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun Sum1_bTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Primary,
            secondary = Secondary,
            background = Background,
            surface = Surface,
            error = Error,
            onPrimary = OnPrimary,
            onSecondary = OnSecondary,
            onBackground = OnBackground,
            onSurface = OnSurface
        )
    } else {
        lightColorScheme(
            primary = Primary,
            secondary = Secondary,
            background = Background,
            surface = Surface,
            error = Error,
            onPrimary = OnPrimary,
            onSecondary = OnSecondary,
            onBackground = OnBackground,
            onSurface = OnSurface
        )
}

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
