package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _alertCircleIcon: ImageVector? = null
val AlertCircleIcon: ImageVector
    get() {
        if (_alertCircleIcon != null) return _alertCircleIcon!!
        _alertCircleIcon = ImageVector.Builder(
            name = "AlertCircleIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                // M12.0002,1.9999
                moveTo(12.0002f, 1.9999f)
                // h0.2
                horizontalLineToRelative(0.2f)
                // c5.399,0 9.8,4.4 9.8,9.8
                curveToRelative(5.399f, 0f, 9.8f, 4.4f, 9.8f, 9.8f)
                // c0,5.6 -4.401,10.1 -10,10.2
                curveToRelative(0f, 5.6f, -4.401f, 10.1f, -10f, 10.2f)
                // h-0.2
                horizontalLineToRelative(-0.2f)
                // c-5.4,0 -9.8,-4.4 -9.8,-9.8
                curveToRelative(-5.4f, 0f, -9.8f, -4.4f, -9.8f, -9.8f)
                // c0,-2.7 1,-5.3 2.9,-7.2
                curveToRelative(0f, -2.7f, 1f, -5.3f, 2.9f, -7.2f)
                // c1.9,-1.9 4.4,-3 7.1,-3
                curveToRelative(1.9f, -1.9f, 4.4f, -3f, 7.1f, -3f)
                close()
                // M11.2002,7.58
                moveTo(11.2002f, 7.58f)
                // v5
                verticalLineToRelative(5f)
                // c0,0.5 0.3,0.8 0.8,0.8
                curveToRelative(0f, 0.5f, 0.3f, 0.8f, 0.8f, 0.8f)
                // c0.4,0 0.8,-0.3 0.8,-0.8
                curveToRelative(0.4f, 0f, 0.8f, -0.3f, 0.8f, -0.8f)
                // v-5
                verticalLineToRelative(-5f)
                // c0,-0.4 -0.3,-0.8 -0.8,-0.8
                curveToRelative(0f, -0.4f, -0.3f, -0.8f, -0.8f, -0.8f)
                // c-0.4,0 -0.8,0.3 -0.8,0.8
                curveToRelative(-0.4f, 0f, -0.8f, 0.3f, -0.8f, 0.8f)
                close()
                // M12,17.2301
                moveTo(12f, 17.2301f)
                // c0.718,0 1.3,-0.582 1.3,-1.3
                curveToRelative(0.718f, 0f, 1.3f, -0.582f, 1.3f, -1.3f)
                // c0,-0.718 -0.582,-1.3 -1.3,-1.3
                curveToRelative(0f, -0.718f, -0.582f, -1.3f, -1.3f, -1.3f)
                // c-0.718,0 -1.3,0.582 -1.3,1.3
                curveToRelative(-0.718f, 0f, -1.3f, 0.582f, -1.3f, 1.3f)
                // c0,0.718 0.582,1.3 1.3,1.3
                curveToRelative(0f, 0.718f, 0.582f, 1.3f, 1.3f, 1.3f)
                close()
            }
        }.build()
        return _alertCircleIcon!!
    }
