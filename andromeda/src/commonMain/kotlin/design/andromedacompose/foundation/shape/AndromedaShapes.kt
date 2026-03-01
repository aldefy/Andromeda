package design.andromedacompose.foundation.shape

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.ui.graphics.Shape

interface BasicShapes {
    val small: CornerBasedShape
    val normal: CornerBasedShape
    val large: CornerBasedShape
}

interface AndromedaShapes : BasicShapes {
    val bottomSheet: Shape
    val buttonShape: Shape
    val dialogShape: Shape
}
