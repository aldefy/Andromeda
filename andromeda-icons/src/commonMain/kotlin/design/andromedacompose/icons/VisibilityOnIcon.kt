package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _visibilityOnIcon: ImageVector? = null
val VisibilityOnIcon: ImageVector
    get() {
        if (_visibilityOnIcon != null) return _visibilityOnIcon!!
        _visibilityOnIcon =
            ImageVector.Builder(
                name = "VisibilityOnIcon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    // M12,4.5
                    moveTo(12f, 4.5f)
                    // C7,4.5 2.73,7.61 1,12
                    curveTo(7f, 4.5f, 2.73f, 7.61f, 1f, 12f)
                    // c1.73,4.39 6,7.5 11,7.5
                    curveToRelative(1.73f, 4.39f, 6f, 7.5f, 11f, 7.5f)
                    // s9.27,-3.11 11,-7.5
                    reflectiveCurveToRelative(9.27f, -3.11f, 11f, -7.5f)
                    // c-1.73,-4.39 -6,-7.5 -11,-7.5
                    curveToRelative(-1.73f, -4.39f, -6f, -7.5f, -11f, -7.5f)
                    close()
                    // M12,17
                    moveTo(12f, 17f)
                    // c-2.76,0 -5,-2.24 -5,-5
                    curveToRelative(-2.76f, 0f, -5f, -2.24f, -5f, -5f)
                    // s2.24,-5 5,-5
                    reflectiveCurveToRelative(2.24f, -5f, 5f, -5f)
                    // s5,2.24 5,5
                    reflectiveCurveToRelative(5f, 2.24f, 5f, 5f)
                    // s-2.24,5 -5,5
                    reflectiveCurveToRelative(-2.24f, 5f, -5f, 5f)
                    close()
                    // M12,9
                    moveTo(12f, 9f)
                    // c-1.66,0 -3,1.34 -3,3
                    curveToRelative(-1.66f, 0f, -3f, 1.34f, -3f, 3f)
                    // s1.34,3 3,3
                    reflectiveCurveToRelative(1.34f, 3f, 3f, 3f)
                    // s3,-1.34 3,-3
                    reflectiveCurveToRelative(3f, -1.34f, 3f, -3f)
                    // s-1.34,-3 -3,-3
                    reflectiveCurveToRelative(-1.34f, -3f, -3f, -3f)
                    close()
                }
            }.build()
        return _visibilityOnIcon!!
    }
