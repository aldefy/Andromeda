package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _photosIcon: ImageVector? = null
val PhotosIcon: ImageVector
    get() {
        if (_photosIcon != null) return _photosIcon!!
        _photosIcon =
            ImageVector.Builder(
                name = "PhotosIcon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    // M22,16
                    moveTo(22f, 16f)
                    // L22,4
                    lineTo(22f, 4f)
                    // c0,-1.1 -0.9,-2 -2,-2
                    curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
                    // L8,2
                    lineTo(8f, 2f)
                    // c-1.1,0 -2,0.9 -2,2
                    curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
                    // v12
                    verticalLineToRelative(12f)
                    // c0,1.1 0.9,2 2,2
                    curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                    // h12
                    horizontalLineToRelative(12f)
                    // c1.1,0 2,-0.9 2,-2
                    curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                    close()
                    // M11,12
                    moveTo(11f, 12f)
                    // l2.03,2.71
                    lineToRelative(2.03f, 2.71f)
                    // L16,11
                    lineTo(16f, 11f)
                    // l4,5
                    lineToRelative(4f, 5f)
                    // L8,16
                    lineTo(8f, 16f)
                    // l3,-4
                    lineToRelative(3f, -4f)
                    close()
                    // M2,6
                    moveTo(2f, 6f)
                    // v14
                    verticalLineToRelative(14f)
                    // c0,1.1 0.9,2 2,2
                    curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                    // h14
                    horizontalLineToRelative(14f)
                    // v-2
                    verticalLineToRelative(-2f)
                    // L4,20
                    lineTo(4f, 20f)
                    // L4,6
                    lineTo(4f, 6f)
                    // L2,6
                    lineTo(2f, 6f)
                    close()
                }
            }.build()
        return _photosIcon!!
    }
