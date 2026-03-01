package design.andromedacompose.catalog

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.reveal.CircularReveal
import androidx.compose.ui.graphics.Color as ComposeColor

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            var isLightTheme by remember { mutableStateOf(true) }

            // Re-apply edge-to-edge with the correct SystemBarStyle whenever
            // the app theme changes. This tells the system whether the status
            // bar background is light or dark so it picks the right icon tint.
            // Works on API < 36; on API 36 the system may override this, so we
            // also draw a scrim (StatusBarProtection) as a fallback.
            SideEffect {
                val barStyle = if (isLightTheme) {
                    SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                } else {
                    SystemBarStyle.dark(Color.TRANSPARENT)
                }
                enableEdgeToEdge(
                    statusBarStyle = barStyle,
                    navigationBarStyle = barStyle,
                )
            }

            CircularReveal(
                targetState = isLightTheme,
                animationSpec = tween(2500)
            ) { localTheme ->
                CatalogTheme(isLightTheme = localTheme) {
                    Box(Modifier.fillMaxSize()) {
                        NavGraph(
                            onToggleTheme = {
                                isLightTheme = !isLightTheme
                            },
                        )
                        // Gradient scrim over status bar for contrast on API 36+
                        // where the system ignores isAppearanceLightStatusBars.
                        if (!localTheme) {
                            StatusBarProtection(
                                color = AndromedaTheme.colors.primaryColors.background
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Draws a gradient scrim over the status bar area. This ensures status bar
 * icons remain readable when the app's dark background can't be communicated
 * to the system (e.g. API 36 where the system controls icon tint based on
 * the system theme, not the app theme).
 */
@Composable
private fun StatusBarProtection(
    color: ComposeColor,
) {
    val statusBarsInsets = WindowInsets.statusBars
    val density = LocalDensity.current
    val heights = remember(statusBarsInsets, density) {
        val statusBarH = statusBarsInsets.getTop(density).toFloat()
        statusBarH to statusBarH * 1.3f
    }
    Canvas(Modifier.fillMaxSize()) {
        val (solidHeight, totalHeight) = heights
        // Solid opaque bar covering the status bar icons
        drawRect(
            color = color,
            size = Size(size.width, solidHeight),
        )
        // Short gradient fade below the solid bar
        val fade = Brush.verticalGradient(
            colors = listOf(color, ComposeColor.Transparent),
            startY = solidHeight,
            endY = totalHeight,
        )
        drawRect(
            brush = fade,
            topLeft = androidx.compose.ui.geometry.Offset(0f, solidHeight),
            size = Size(size.width, totalHeight - solidHeight),
        )
    }
}
