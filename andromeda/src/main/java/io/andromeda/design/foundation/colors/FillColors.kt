package io.andromeda.design.foundation.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color as ComposeColor

interface FillColors {
    val background: ComposeColor
    val active: ComposeColor
    val error: ComposeColor
    val mute: ComposeColor
    val pressed: ComposeColor
}

@Stable
class PrimaryColors(
    background: ComposeColor,
    active: ComposeColor,
    error: ComposeColor,
    mute: ComposeColor,
    pressed: ComposeColor,
) : FillColors {
    override var background: ComposeColor by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    override var active: ComposeColor by mutableStateOf(active, structuralEqualityPolicy())
        internal set
    override var error: ComposeColor by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    override var mute: ComposeColor by mutableStateOf(mute, structuralEqualityPolicy())
        internal set
    override var pressed: ComposeColor by mutableStateOf(pressed, structuralEqualityPolicy())
        internal set

    fun copy(
        background: ComposeColor = this.background,
        active: ComposeColor = this.active,
        error: ComposeColor = this.error,
        mute: ComposeColor = this.mute,
        pressed: ComposeColor = this.pressed
    ): PrimaryColors = PrimaryColors(
        active,
        background,
        error,
        mute,
        pressed,
    )
}

@Stable
class SecondaryColors(
    background: ComposeColor,
    active: ComposeColor,
    error: ComposeColor,
    mute: ComposeColor,
    pressed: ComposeColor,
) : FillColors {
    override var background: ComposeColor by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    override var active: ComposeColor by mutableStateOf(active, structuralEqualityPolicy())
        internal set
    override var error: ComposeColor by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    override var mute: ComposeColor by mutableStateOf(mute, structuralEqualityPolicy())
        internal set
    override var pressed: ComposeColor by mutableStateOf(pressed, structuralEqualityPolicy())
        internal set

    fun copy(
        background: ComposeColor = this.background,
        active: ComposeColor = this.active,
        error: ComposeColor = this.error,
        mute: ComposeColor = this.mute,
        pressed: ComposeColor = this.pressed
    ): SecondaryColors = SecondaryColors(
        active,
        background,
        error,
        mute,
        pressed,
    )
}

@Stable
class TertiaryColors(
    background: ComposeColor,
    active: ComposeColor,
    error: ComposeColor,
    mute: ComposeColor,
    pressed: ComposeColor,
) : FillColors {
    override var background: ComposeColor by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    override var active: ComposeColor by mutableStateOf(active, structuralEqualityPolicy())
        internal set
    override var error: ComposeColor by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    override var mute: ComposeColor by mutableStateOf(mute, structuralEqualityPolicy())
        internal set
    override var pressed: ComposeColor by mutableStateOf(pressed, structuralEqualityPolicy())
        internal set

    fun copy(
        background: ComposeColor = this.background,
        active: ComposeColor = this.active,
        error: ComposeColor = this.error,
        mute: ComposeColor = this.mute,
        pressed: ComposeColor = this.pressed
    ): TertiaryColors = TertiaryColors(
        active,
        background,
        error,
        mute,
        pressed,
    )
}
