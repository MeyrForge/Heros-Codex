package com.meyrforge.heroscodex.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = MagicalGold,
  onPrimary = DarkBackgroundEnd,
  primaryContainer = DarkBackgroundStart,
  onPrimaryContainer = ParchmentText,

  secondary = MagicalGold,
  onSecondary = DarkBackgroundEnd,
  secondaryContainer = DarkBackgroundStart,
  onSecondaryContainer = ParchmentText,

  tertiary = MagicalGold,
  onTertiary = ParchmentText,
  tertiaryContainer = DarkBackgroundStart,
  onTertiaryContainer = ParchmentText,

  background = DarkBackgroundStart,
  onBackground = ParchmentText,

  surface = DarkBackgroundStart,
  onSurface = ParchmentText,

  surfaceVariant = DarkBackgroundEnd,
  onSurfaceVariant = ParchmentText,

  outline = MagicalGold.copy(alpha = 0.3f),
  outlineVariant = DarkBackgroundEnd,

  error = Error,
  onError = OnStateLightText,
)

@Composable
fun HerosCodexTheme(
  content: @Composable () -> Unit
) {
  val colorScheme = DarkColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = MedievalTypography,
    content = content
  )
}
