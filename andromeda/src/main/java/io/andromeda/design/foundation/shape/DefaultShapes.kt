package io.andromeda.design.foundation.shape

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Contains default shapes this library provides for components.
 *
 * @param bottomSheet - The shape of components used as bottom sheets.
 * @param buttonShape - The shape of components used as buttons.
 * @param dialogShape - The shape of components used for showing dialog box.
 * */
public class DefaultShapes(
    override val bottomSheet: Shape,
    override val buttonShape: Shape,
    override val dialogShape: Shape,
    override val small: CornerBasedShape,
    override val normal: CornerBasedShape,
    override val large: CornerBasedShape,
) : AndromedaShapes {
    public companion object {
        public val default: AndromedaShapes = DefaultShapes(
            bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            buttonShape = RoundedCornerShape(8.dp),
            dialogShape = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(4.dp),
            normal = RoundedCornerShape(6.dp),
            large = RoundedCornerShape(12.dp),
        )
    }
}

/**
 * Local providers for shapes in [AndromedaTheme].
 * */
internal val LocalShapes = compositionLocalOf<AndromedaShapes> {
    error(
        "No shapes provided! Make sure to wrap all usages of Andromeda components in a " +
                "AndromedaTheme."
    )
}
