package io.andromeda.design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import io.andromeda.design.foundation.colors.AndromedaColors
import io.andromeda.design.foundation.colors.LocalColors

@Composable
fun AndromedaTheme(
    colors: AndromedaColors = AndromedaTheme.colors,
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors }
    CompositionLocalProvider(
        LocalColors provides colors,
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
