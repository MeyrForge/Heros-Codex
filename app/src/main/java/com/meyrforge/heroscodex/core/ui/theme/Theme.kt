package com.meyrforge.heroscodex.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme: ColorScheme = darkColorScheme(
  primary = PrimaryRed,
  onPrimary = TextOverRed,
  primaryContainer = HoverRed,
  onPrimaryContainer = TextOverRed,

  secondary = Gold,
  onSecondary = TertiaryText,
  secondaryContainer = Cards,
  onSecondaryContainer = PrimaryText,

  tertiary = LightRed,
  onTertiary = TextOverRed,
  tertiaryContainer = Cards,
  onTertiaryContainer = PrimaryText,

  background = Background,
  onBackground = PrimaryText,

  surface = Cards,
  onSurface = PrimaryText,

  surfaceVariant = Cards2,
  onSurfaceVariant = FourthText,

  // Outline / Dividers
  outline = Outline,
  outlineVariant = Divider,

  // States
  error = Error,
  onError = OnStateLightText,
)

private val LightColorScheme: ColorScheme = lightColorScheme(
  primary = PrimaryRed,
  onPrimary = TextOverRed,
  primaryContainer = LightRed,
  onPrimaryContainer = TextOverRed,

  secondary = Gold,
  onSecondary = TertiaryText,
  secondaryContainer = Cards2,
  onSecondaryContainer = TertiaryText,

  tertiary = Cards,
  onTertiary = PrimaryText,
  tertiaryContainer = Cards2,
  onTertiaryContainer = TertiaryText,

  background = Cards2,
  onBackground = TertiaryText,

  surface = Cards2,
  onSurface = TertiaryText,

  surfaceVariant = Cards,
  onSurfaceVariant = PrimaryText,

  // Outline / Dividers
  outline = Outline,
  outlineVariant = DividerOnLight,

  // States
  error = Error,
  onError = OnStateLightText,
)

@Composable
fun HerosCodexTheme(
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
    typography = MedievalTypography,
    content = content
  )
}
