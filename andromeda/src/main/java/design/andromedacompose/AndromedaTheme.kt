package design.andromedacompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontFamily
import design.andromedacompose.foundation.ContentEmphasis
import design.andromedacompose.foundation.LocalContentEmphasis
import design.andromedacompose.foundation.colors.AndromedaColors
import design.andromedacompose.foundation.colors.LocalColors
import design.andromedacompose.foundation.shape.AndromedaShapes
import design.andromedacompose.foundation.shape.DefaultShapes
import design.andromedacompose.foundation.shape.LocalShapes
import design.andromedacompose.foundation.typography.AndromedaFonts
import design.andromedacompose.foundation.typography.AndromedaTypography
import design.andromedacompose.foundation.typography.LocalTypography
import design.andromedacompose.foundation.typography.textStyles

@Composable
fun AndromedaTheme(
    shapes: AndromedaShapes = DefaultShapes.default,
    fontFamily: FontFamily = AndromedaFonts,
    colors: AndromedaColors = AndromedaTheme.colors,
    typography: AndromedaTypography = textStyles(fontFamily = fontFamily),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalContentEmphasis provides ContentEmphasis.Normal,
    ) {
        content()
    }
}

/**
 * Useful static object to access currently configured Theme properties.
 */
object AndromedaTheme {

    /**
     * These represent the default ease-of-use accessors for colors, typography.
     * */
    public val colors: AndromedaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    public val typography: AndromedaTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    public val shapes: AndromedaShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
