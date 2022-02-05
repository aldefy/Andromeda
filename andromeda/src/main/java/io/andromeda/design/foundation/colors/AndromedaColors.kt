package io.andromeda.design.foundation.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy

@Stable
class AndromedaColors(
    val primaryColors: PrimaryColors,
    val secondaryColors: SecondaryColors,
    val tertiaryColors: TertiaryColors,
    val borderColors: BorderColors,
    val iconColors: IconColors,
    val contentColors: ContentColors,
    isDark: Boolean
) {
    var isDark: Boolean by mutableStateOf(isDark, structuralEqualityPolicy())
        internal set

    fun copy(
        primaryColors: PrimaryColors = this.primaryColors,
        secondaryColors: SecondaryColors = this.secondaryColors,
        tertiaryColors: TertiaryColors = this.tertiaryColors,
        borderColors: BorderColors = this.borderColors,
        iconColors: IconColors = this.iconColors,
        contentColors: ContentColors = this.contentColors,
        isDark: Boolean = this.isDark
    ): AndromedaColors = AndromedaColors(
        primaryColors = primaryColors,
        secondaryColors = secondaryColors,
        tertiaryColors = tertiaryColors,
        borderColors = borderColors,
        iconColors = iconColors,
        contentColors = contentColors,
        isDark = isDark
    )
}


/**
 * Local Andromeda Colors.
 *
 * Access the colors through [AndromedaTheme.colors]
 */
internal val LocalColors: ProvidableCompositionLocal<AndromedaColors> =
    compositionLocalOf { defaultLightColors() }

@Composable
public fun ProvideColors(colors: AndromedaColors, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}
