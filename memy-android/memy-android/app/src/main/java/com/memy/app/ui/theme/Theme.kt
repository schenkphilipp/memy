package com.memy.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Memy uses a light-only theme (no dark mode in v1 spec).
private val MemyColorScheme = lightColorScheme(
    primary          = Brand,          // #FF8383
    onPrimary        = OnBrand,        // #FFFCFC
    primaryContainer = BrandWash,      // #FFF5F5
    onPrimaryContainer = BrandStrong,  // #EF6D6D

    secondary        = Neutral800,
    onSecondary      = White,
    secondaryContainer = SurfaceSunken,
    onSecondaryContainer = TextStrong,

    tertiary         = Success,
    onTertiary       = White,

    background       = SurfacePage,    // #FFFCFC
    onBackground     = TextStrong,     // #212121

    surface          = SurfaceCard,    // #FFFFFF
    onSurface        = TextStrong,
    surfaceVariant   = SurfaceSunken,  // #F5F5F5
    onSurfaceVariant = TextBody,

    outline          = BorderDefault,  // #DADADA
    outlineVariant   = BorderSubtle,   // #E0E0E0

    error            = Danger,
    onError          = White,
)

// Shapes from the token spec: small 8 / medium 12 / large 20
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val MemyShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small      = RoundedCornerShape(8.dp),
    medium     = RoundedCornerShape(12.dp),
    large      = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

@Composable
fun MemyTheme(
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SurfacePage.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = MemyColorScheme,
        typography  = MemyTypography,
        shapes      = MemyShapes,
        content     = content,
    )
}
