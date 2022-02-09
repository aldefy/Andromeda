package design.andromedacompose.foundation.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.material.LocalContentColor
import androidx.compose.ui.graphics.takeOrElse
import design.andromedacompose.AndromedaTheme
import androidx.compose.ui.graphics.Color as ComposeColor

@Stable
class ContentColors(
    normal: ComposeColor,
    minor: ComposeColor,
    subtle: ComposeColor,
    disabled: ComposeColor,
) {
    var normal: ComposeColor by mutableStateOf(normal, structuralEqualityPolicy())
        internal set
    var minor: ComposeColor by mutableStateOf(minor, structuralEqualityPolicy())
        internal set
    var subtle: ComposeColor by mutableStateOf(subtle, structuralEqualityPolicy())
        internal set
    var disabled: ComposeColor by mutableStateOf(disabled, structuralEqualityPolicy())
        internal set

    fun copy(
        normal: ComposeColor = this.normal,
        minor: ComposeColor = this.minor,
        subtle: ComposeColor = this.subtle,
        disabled: ComposeColor = this.disabled,
    ): ContentColors = ContentColors(
        normal,
        minor,
        subtle,
        disabled,
    )
}

@Composable
public fun contentColorFor(backgroundColor: ComposeColor): ComposeColor =
    AndromedaTheme.colors.contentColorFor(backgroundColor)
        .takeOrElse { LocalContentColor.current }

public val LocalContentColor: ProvidableCompositionLocal<ComposeColor> = LocalContentColor
