package io.andromeda.design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import io.andromeda.design.foundation.colors.AndromedaColors
import io.andromeda.design.foundation.colors.LocalColors
import io.andromeda.design.foundation.shape.AndromedaShapes
import io.andromeda.design.foundation.shape.DefaultShapes
import io.andromeda.design.foundation.shape.LocalShapes

@Composable
fun AndromedaTheme(
    colors: AndromedaColors = AndromedaTheme.colors,
    shapes: AndromedaShapes = DefaultShapes.default,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
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
     * These represent the default ease-of-use accessors for colors
     * */
    public val colors: AndromedaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}
