package design.andromedacompose.foundation.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import design.andromedacompose.andromeda.generated.resources.Res
import design.andromedacompose.andromeda.generated.resources.andromeda_black
import design.andromedacompose.andromeda.generated.resources.andromeda_bold
import design.andromedacompose.andromeda.generated.resources.andromeda_extrabold
import design.andromedacompose.andromeda.generated.resources.andromeda_light
import design.andromedacompose.andromeda.generated.resources.andromeda_medium
import design.andromedacompose.andromeda.generated.resources.andromeda_regular
import design.andromedacompose.andromeda.generated.resources.andromeda_semibold
import design.andromedacompose.andromeda.generated.resources.andromeda_thin
import org.jetbrains.compose.resources.Font

/**
 * Fallback font family used by typography style classes that are not constructed
 * through the theme. The actual Andromeda fonts are loaded via [andromedaFontFamily]
 * and injected through [AndromedaTheme].
 */
val AndromedaFonts = FontFamily.Default

/**
 * Loads the Andromeda font family from Compose Multiplatform resources.
 * Must be called from a @Composable context.
 */
@Composable
fun andromedaFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.andromeda_black, FontWeight.Black),
        Font(Res.font.andromeda_bold, FontWeight.Bold),
        Font(Res.font.andromeda_extrabold, FontWeight.ExtraBold),
        Font(Res.font.andromeda_light, FontWeight.Light),
        Font(Res.font.andromeda_medium, FontWeight.Medium),
        Font(Res.font.andromeda_regular, FontWeight.W400),
        Font(Res.font.andromeda_semibold, FontWeight.SemiBold),
        Font(Res.font.andromeda_thin, FontWeight.Thin),
    )
