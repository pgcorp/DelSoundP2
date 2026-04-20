package com.example.delsound.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

//Crear y modificar hasta que quede este codigo
private val DarkColorScheme = darkColorScheme(
    primary = SoundInPrimaryDark,
    secondary = SoundInSecondaryDark,
    //tertiary = Pink80
    background = SoundInBackgroundDark,
    surface = SoundInSurfaceDark,
    onPrimary = Color(0xFF1E1B4B),
    onBackground = Color(0xFFE8E8FF),
    onSurface = Color(0XFFE8E8FF),

    )
//igual crear y modificar hasta que quede este codigo

private val LightColorScheme = lightColorScheme(
    primary = SoundInPrimary,
    secondary = SoundInSecondary,
    // tertiary = Pink40,
    error = SoundInError,
    background = SoundInBackground,
    surface = SoundInSurface,
    onPrimary = Color.White,
    //  onSecondary = Color.White,
    // onTertiary = Color.White,
    onBackground = Color(0xFF1F1F2E),
    onSurface = Color(0xFF1F1F2E),
    )

@Composable
fun DelSoundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(16.dp),
            extraLarge = RoundedCornerShape(16.dp)
        ),
        content = content
    )
}