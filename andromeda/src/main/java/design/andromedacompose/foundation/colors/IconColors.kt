package design.andromedacompose.foundation.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color as ComposeColor

interface IconColors {
    val default: ComposeColor
    val disabled: ComposeColor
    val active: ComposeColor
}

@Stable
class AndromedaIconColors(
    default: ComposeColor,
    disabled: ComposeColor,
    active: ComposeColor,
) : IconColors {
    override var default: ComposeColor by mutableStateOf(default, structuralEqualityPolicy())
        internal set
    override var disabled: ComposeColor by mutableStateOf(disabled, structuralEqualityPolicy())
        internal set
    override var active: ComposeColor by mutableStateOf(active, structuralEqualityPolicy())
        internal set

    fun copy(
        default: ComposeColor = this.default,
        disabled: ComposeColor = this.disabled,
        active: ComposeColor = this.active,
    ): IconColors = AndromedaIconColors(
        default,
        disabled,
        active,
    )
}
