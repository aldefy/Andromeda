package io.andromeda.design.foundation.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color as ComposeColor

interface BorderColors {
    val active: ComposeColor
    val pressed: ComposeColor
    val inactive: ComposeColor
    val mute: ComposeColor
    val focus: ComposeColor
    val error: ComposeColor
}

@Stable
class StrokeColors(
    active: ComposeColor,
    pressed: ComposeColor,
    inactive: ComposeColor,
    mute: ComposeColor,
    focus: ComposeColor,
    error: ComposeColor,
) : BorderColors {
    override var active: ComposeColor by mutableStateOf(active, structuralEqualityPolicy())
        internal set
    override var inactive: ComposeColor by mutableStateOf(inactive, structuralEqualityPolicy())
        internal set
    override var error: ComposeColor by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    override var mute: ComposeColor by mutableStateOf(mute, structuralEqualityPolicy())
        internal set
    override var pressed: ComposeColor by mutableStateOf(pressed, structuralEqualityPolicy())
        internal set
    override var focus: ComposeColor by mutableStateOf(focus, structuralEqualityPolicy())
        internal set

    fun copy(
        active: ComposeColor = this.active,
        pressed: ComposeColor = this.pressed,
        inactive: ComposeColor = this.inactive,
        mute: ComposeColor,
        focus: ComposeColor,
        error: ComposeColor,
    ): StrokeColors = StrokeColors(
        active,
        pressed,
        inactive,
        mute,
        focus,
        error
    )
}
