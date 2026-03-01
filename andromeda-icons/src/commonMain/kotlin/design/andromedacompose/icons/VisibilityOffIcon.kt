package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _visibilityOffIcon: ImageVector? = null
val VisibilityOffIcon: ImageVector
    get() {
        if (_visibilityOffIcon != null) return _visibilityOffIcon!!
        _visibilityOffIcon =
            ImageVector.Builder(
                name = "VisibilityOffIcon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    // M12,7
                    moveTo(12f, 7f)
                    // c2.76,0 5,2.24 5,5
                    curveToRelative(2.76f, 0f, 5f, 2.24f, 5f, 5f)
                    // c0,0.65 -0.13,1.26 -0.36,1.83
                    curveToRelative(0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
                    // l2.92,2.92
                    lineToRelative(2.92f, 2.92f)
                    // c1.51,-1.26 2.7,-2.89 3.43,-4.75
                    curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
                    // c-1.73,-4.39 -6,-7.5 -11,-7.5
                    curveToRelative(-1.73f, -4.39f, -6f, -7.5f, -11f, -7.5f)
                    // c-1.4,0 -2.74,0.25 -3.98,0.7
                    curveToRelative(-1.4f, 0f, -2.74f, 0.25f, -3.98f, 0.7f)
                    // l2.16,2.16
                    lineToRelative(2.16f, 2.16f)
                    // C10.74,7.13 11.35,7 12,7
                    curveTo(10.74f, 7.13f, 11.35f, 7f, 12f, 7f)
                    close()
                    // M2,4.27
                    moveTo(2f, 4.27f)
                    // l2.28,2.28
                    lineToRelative(2.28f, 2.28f)
                    // l0.46,0.46 (space = next coordinate pair)
                    lineToRelative(0.46f, 0.46f)
                    // C3.08,8.3 1.78,10.02 1,12
                    curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1f, 12f)
                    // c1.73,4.39 6,7.5 11,7.5
                    curveToRelative(1.73f, 4.39f, 6f, 7.5f, 11f, 7.5f)
                    // c1.55,0 3.03,-0.3 4.38,-0.84
                    curveToRelative(1.55f, 0f, 3.03f, -0.3f, 4.38f, -0.84f)
                    // l0.42,0.42
                    lineToRelative(0.42f, 0.42f)
                    // L19.73,22
                    lineTo(19.73f, 22f)
                    // L21,20.73
                    lineTo(21f, 20.73f)
                    // L3.27,3
                    lineTo(3.27f, 3f)
                    // L2,4.27
                    lineTo(2f, 4.27f)
                    close()
                    // M7.53,9.8
                    moveTo(7.53f, 9.8f)
                    // l1.55,1.55
                    lineToRelative(1.55f, 1.55f)
                    // c-0.05,0.21 -0.08,0.43 -0.08,0.65
                    curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                    // c0,1.66 1.34,3 3,3
                    curveToRelative(0f, 1.66f, 1.34f, 3f, 3f, 3f)
                    // c0.22,0 0.44,-0.03 0.65,-0.08
                    curveToRelative(0.22f, 0f, 0.44f, -0.03f, 0.65f, -0.08f)
                    // l1.55,1.55
                    lineToRelative(1.55f, 1.55f)
                    // c-0.67,0.33 -1.41,0.53 -2.2,0.53
                    curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
                    // c-2.76,0 -5,-2.24 -5,-5
                    curveToRelative(-2.76f, 0f, -5f, -2.24f, -5f, -5f)
                    // c0,-0.79 0.2,-1.53 0.53,-2.2
                    curveToRelative(0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
                    close()
                    // M11.84,9.02
                    moveTo(11.84f, 9.02f)
                    // l3.15,3.15
                    lineToRelative(3.15f, 3.15f)
                    // l0.02,-0.16 (space = next pair)
                    lineToRelative(0.02f, -0.16f)
                    // c0,-1.66 -1.34,-3 -3,-3
                    curveToRelative(0f, -1.66f, -1.34f, -3f, -3f, -3f)
                    // l-0.17,0.01
                    lineToRelative(-0.17f, 0.01f)
                    close()
                }
            }.build()
        return _visibilityOffIcon!!
    }
