package design.andromedacompose.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import design.andromedacompose.AndromedaTheme
import androidx.compose.ui.graphics.Color as ComposeColor

public enum class ContentEmphasis {
    Normal,
    Minor,
    Subtle,
    Disabled,
}

public val LocalContentEmphasis: ProvidableCompositionLocal<ContentEmphasis> =
    compositionLocalOf { ContentEmphasis.Normal }

@Composable
public fun ProvideContentEmphasis(emphasis: ContentEmphasis, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalContentEmphasis provides emphasis, content = content)
}

@Composable
public fun ComposeColor.applyEmphasis(emphasis: ContentEmphasis): ComposeColor =
    when (emphasis) {
        ContentEmphasis.Normal -> {
            this
        }
        ContentEmphasis.Minor -> {
            val colors = AndromedaTheme.colors
            if (colors.contentColors.normal == this) {
                colors.contentColors.minor
            } else {
                copy(alpha = 0.80f)
            }
        }
        ContentEmphasis.Subtle -> {
            val colors = AndromedaTheme.colors
            if (colors.contentColors.normal == this) {
                colors.contentColors.subtle
            } else {
                copy(alpha = 0.66f)
            }
        }
        ContentEmphasis.Disabled -> {
            val colors = AndromedaTheme.colors
            if (colors.contentColors.normal == this) {
                colors.contentColors.disabled
            } else {
                copy(alpha = 0.48f)
            }
        }
    }
