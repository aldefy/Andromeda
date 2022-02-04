package io.andromeda.design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontFamily
import io.andromeda.design.foundation.colors.AndromedaColors
import io.andromeda.design.foundation.colors.LocalColors
import io.andromeda.design.foundation.shape.AndromedaShapes
import io.andromeda.design.foundation.shape.DefaultShapes
import io.andromeda.design.foundation.shape.LocalShapes
import io.andromeda.design.foundation.typography.AndromedaFonts
import io.andromeda.design.foundation.typography.AndromedaTypography
import io.andromeda.design.foundation.typography.LocalTypography
import io.andromeda.design.foundation.typography.textStyles

@Composable
fun AndromedaTheme(
    colors: AndromedaColors = AndromedaTheme.colors,
    shapes: AndromedaShapes = DefaultShapes.default,
    fontFamily: FontFamily = AndromedaFonts,
    typography: AndromedaTypography = textStyles(fontFamily = fontFamily),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalShapes provides shapes,
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
