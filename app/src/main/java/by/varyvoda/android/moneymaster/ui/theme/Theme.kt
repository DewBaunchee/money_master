package by.varyvoda.android.moneymaster.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGray,
    secondary = PrimaryGray,
    tertiary = PrimaryGray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGray,
    onPrimary = Secondary,
//    primaryContainer = ColorLightTokens.PrimaryContainer,
//    onPrimaryContainer = ColorLightTokens.OnPrimaryContainer,
//    inversePrimary = ColorLightTokens.InversePrimary,

    secondary = Secondary,
    onSecondary = SecondaryContainer,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = Secondary,

    tertiary = PrimaryGray,
//    onTertiary = ColorLightTokens.OnTertiary,
//    tertiaryContainer = ColorLightTokens.TertiaryContainer,
//    onTertiaryContainer = ColorLightTokens.OnTertiaryContainer,
//
    background = Background,
//    onBackground = ColorLightTokens.OnBackground,
//
    surface = Surface,
//    onSurface = ColorLightTokens.OnSurface,
//    surfaceVariant = ColorLightTokens.SurfaceVariant,
//    onSurfaceVariant = ColorLightTokens.OnSurfaceVariant,
//    surfaceTint = PrimaryGray,
//    inverseSurface = ColorLightTokens.InverseSurface,
//    inverseOnSurface = ColorLightTokens.InverseOnSurface,
//
//    error = ColorLightTokens.Error,
//    onError = ColorLightTokens.OnError,
//    errorContainer = ColorLightTokens.ErrorContainer,
//    onErrorContainer = ColorLightTokens.OnErrorContainer,
//
//    outline = ColorLightTokens.Outline,
//    outlineVariant = ColorLightTokens.OutlineVariant,
//
//    scrim = ColorLightTokens.Scrim,
//
//    surfaceBright = ColorLightTokens.SurfaceBright,
//    surfaceContainer = ColorLightTokens.SurfaceContainer,
//    surfaceContainerHigh = ColorLightTokens.SurfaceContainerHigh,
//    surfaceContainerHighest = ColorLightTokens.SurfaceContainerHighest,
//    surfaceContainerLow = ColorLightTokens.SurfaceContainerLow,
//    surfaceContainerLowest = ColorLightTokens.SurfaceContainerLowest,
//    surfaceDim = ColorLightTokens.SurfaceDim,
)

@Composable
fun MoneyMasterTheme(
    darkTheme: Boolean = false,
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
        colorScheme = LightColorScheme, // FIXME
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}