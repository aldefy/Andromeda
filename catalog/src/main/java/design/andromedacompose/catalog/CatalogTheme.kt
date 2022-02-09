package design.andromedacompose.catalog

import androidx.compose.runtime.Composable
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.foundation.colors.defaultDarkColors
import design.andromedacompose.foundation.colors.defaultLightColors

@Composable
fun CatalogTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    AndromedaTheme(
        colors = if (isLightTheme) defaultLightColors() else defaultDarkColors(),
    ) {
        content()
    }
}
