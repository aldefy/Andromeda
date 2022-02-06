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
    val alt: ComposeColor
}

@Stable
class PrimaryColors(
    background: ComposeColor,
    active: ComposeColor,
    error: ComposeColor,
    mute: ComposeColor,
    pressed: ComposeColor,
    alt: ComposeColor
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
    override var alt: ComposeColor by mutableStateOf(alt, structuralEqualityPolicy())
        internal set

    fun copy(
        background: ComposeColor = this.background,
        active: ComposeColor = this.active,
        error: ComposeColor = this.error,
        mute: ComposeColor = this.mute,
        pressed: ComposeColor = this.pressed,
        alt: ComposeColor = this.alt
    ): PrimaryColors = PrimaryColors(
        active,
        background,
        error,
        mute,
        pressed,
        alt
    )
}

@Stable
class SecondaryColors(
    background: ComposeColor,
    active: ComposeColor,
    error: ComposeColor,
    mute: ComposeColor,
    pressed: ComposeColor,
    alt: ComposeColor
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
    override var alt: ComposeColor by mutableStateOf(alt, structuralEqualityPolicy())
        internal set

    fun copy(
        background: ComposeColor = this.background,
        active: ComposeColor = this.active,
        error: ComposeColor = this.error,
        mute: ComposeColor = this.mute,
        pressed: ComposeColor = this.pressed,
        alt: ComposeColor = this.alt
    ): SecondaryColors = SecondaryColors(
        active,
        background,
        error,
        mute,
        pressed,
        alt
    )
}

@Stable
class TertiaryColors(
    background: ComposeColor,
    active: ComposeColor,
    error: ComposeColor,
    mute: ComposeColor,
    pressed: ComposeColor,
    alt: ComposeColor
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
    override var alt: ComposeColor by mutableStateOf(alt, structuralEqualityPolicy())
        internal set

    fun copy(
        background: ComposeColor = this.background,
        active: ComposeColor = this.active,
        error: ComposeColor = this.error,
        mute: ComposeColor = this.mute,
        pressed: ComposeColor = this.pressed,
        alt: ComposeColor = this.alt
    ): TertiaryColors = TertiaryColors(
        active,
        background,
        error,
        mute,
        pressed,
        alt
    )
}

internal fun FillColors.contentColorFor(
    color: ComposeColor
): ComposeColor? =
    when (color) {
        background -> alt
        active -> alt
        error -> alt
        mute -> alt
        pressed -> alt
        else -> null
    }
