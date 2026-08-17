package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = GeoPrimaryDark,
  onPrimary = GeoDarkHeader,
  primaryContainer = GeoPrimaryContainerDark,
  onPrimaryContainer = GeoOnPrimaryContainerDark,
  secondary = GeoGold,
  onSecondary = GeoDarkHeader,
  secondaryContainer = GeoGoldContainer,
  onSecondaryContainer = GeoOnGoldContainer,
  tertiary = GeoEmerald,
  background = GeoBackgroundDark,
  onBackground = GeoTextPrimaryDark,
  surface = GeoSurfaceDark,
  onSurface = GeoTextPrimaryDark,
  surfaceVariant = GeoSurfaceVariantDark,
  onSurfaceVariant = GeoTextMutedDark,
  outline = GeoBorderDark,
  error = GeoError,
)

private val LightColorScheme = lightColorScheme(
  primary = GeoPrimary,
  onPrimary = GeoSurface,
  primaryContainer = GeoPrimaryContainer,
  onPrimaryContainer = GeoOnPrimaryContainer,
  secondary = GeoGold,
  onSecondary = GeoSurface,
  secondaryContainer = GeoGoldContainer,
  onSecondaryContainer = GeoOnGoldContainer,
  tertiary = GeoEmerald,
  background = GeoBackground,
  onBackground = GeoTextPrimary,
  surface = GeoSurface,
  onSurface = GeoTextPrimary,
  surfaceVariant = GeoSurfaceVariant,
  onSurfaceVariant = GeoTextMuted,
  outline = GeoBorder,
  error = GeoError,
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disable dynamic color so our curated Geometric Balance GMYS theme is consistently applied
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
