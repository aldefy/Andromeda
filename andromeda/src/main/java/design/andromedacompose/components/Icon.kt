package design.andromedacompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.design.conditional
import design.andromedacompose.foundation.ContentEmphasis
import design.andromedacompose.foundation.LocalContentEmphasis
import design.andromedacompose.foundation.applyEmphasis
import design.andromedacompose.foundation.colors.LocalContentColor

@Composable
public fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    emphasis: ContentEmphasis = LocalContentEmphasis.current,
    tint: Color = AndromedaTheme.colors.contentColors.normal.applyEmphasis(emphasis),
) {
    Icon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}

@Composable
public fun Icon(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    emphasis: ContentEmphasis = LocalContentEmphasis.current,
    tint: Color = LocalContentColor.current.applyEmphasis(emphasis),
) {
    val painter = remember(bitmap) { BitmapPainter(bitmap) }
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}

@Composable
public fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: IconClickHandler? = null,
    emphasis: ContentEmphasis = LocalContentEmphasis.current,
    tint: Color = AndromedaTheme.colors.contentColors.normal.applyEmphasis(emphasis),
) {
    val colorFilter = if (tint == Color.Unspecified) null else ColorFilter.tint(tint)
    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }
    val iconClickRipple = rememberRipple(
        bounded = false,
        color = AndromedaTheme.colors.contentColors.normal
    )
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeFor(painter)
            .paint(
                painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit
            )
            .then(semantics)
            .conditional(onClick != null) {
                Modifier.clickable(
                    onClick = onClick!!,
                    indication = iconClickRipple,
                    interactionSource = interactionSource
                )
            }
    )
}

private fun Modifier.defaultSizeFor(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            DefaultIconSizeModifier
        } else {
            Modifier
        }
    )

typealias IconClickHandler = () -> Unit

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()
private val DefaultIconSizeModifier = Modifier.size(24.dp)
