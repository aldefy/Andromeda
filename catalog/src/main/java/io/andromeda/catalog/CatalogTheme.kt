package io.andromeda.catalog

import androidx.compose.runtime.Composable
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.foundation.colors.defaultDarkColors
import io.andromeda.design.foundation.colors.defaultLightColors

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
