package com.polly.housecowork.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    onPrimary = DeepYellow,
    primary = Yellow,
    secondary = DeepGrey,
    onSecondary = Gray90,
    tertiary = Green90,
    onTertiary = GreenBlue,
    background = YellowWhite,
    onBackground = Black,
    surface = Dim87,
    surfaceTint = SlightGrey,
    onSurfaceVariant = Skinny,
    onSurface = colorLightOnBackground,
    error = Red
)

private val DarkColorScheme = darkColorScheme(
    primary = Brown30,
    secondary = Black20,
    tertiary = Green30,
    primaryContainer = Yellow80,
    surface = Dim6,
    onBackground = Black4,
    background = Black4
)

private val defaultTypography = HCWTypo
val LocalTypography = compositionLocalOf { defaultTypography }

private val defaultShapes = HCWShapes
val LocalShapes = compositionLocalOf { defaultShapes }

private val defaultColorScheme = LightColorScheme
val LocalColorScheme = compositionLocalOf { defaultColorScheme }

@Composable
@ExperimentalMaterial3Api
fun HouseCoworkTheme(
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

        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HCWTypo,
        shapes = HCWShapes,
        content = content,
    )
}