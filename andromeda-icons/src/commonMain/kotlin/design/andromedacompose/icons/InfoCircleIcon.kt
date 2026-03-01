package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _infoCircleIcon: ImageVector? = null
val InfoCircleIcon: ImageVector
    get() {
        if (_infoCircleIcon != null) return _infoCircleIcon!!
        _infoCircleIcon =
            ImageVector.Builder(
                name = "InfoCircleIcon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    // M14.1667,17
                    moveTo(14.1667f, 17f)
                    // h-3.3334
                    horizontalLineToRelative(-3.3334f)
                    // c-0.5,0 -0.8333,-0.3146 -0.8333,-0.7865
                    curveToRelative(-0.5f, 0f, -0.8333f, -0.3146f, -0.8333f, -0.7865f)
                    // c0,-0.472 0.3333,-0.7865 0.8333,-0.7865
                    curveToRelative(0f, -0.472f, 0.3333f, -0.7865f, 0.8333f, -0.7865f)
                    // H11.5
                    horizontalLineTo(11.5f)
                    // c0.0833,0 0.1667,-0.0787 0.1667,-0.1573
                    curveToRelative(0.0833f, 0f, 0.1667f, -0.0787f, 0.1667f, -0.1573f)
                    // v-3.5394
                    verticalLineToRelative(-3.5394f)
                    // c0,-0.0786 -0.0834,-0.1573 -0.1667,-0.1573
                    curveToRelative(0f, -0.0786f, -0.0834f, -0.1573f, -0.1667f, -0.1573f)
                    // h-0.6667
                    horizontalLineToRelative(-0.6667f)
                    // c-0.5,0 -0.8333,-0.3146 -0.8333,-0.7865
                    curveToRelative(-0.5f, 0f, -0.8333f, -0.3146f, -0.8333f, -0.7865f)
                    // S10.3333,10 10.8333,10
                    reflectiveCurveTo(10.3333f, 10f, 10.8333f, 10f)
                    // h0.8334
                    horizontalLineToRelative(0.8334f)
                    // c0.9166,0 1.6666,0.7079 1.6666,1.573
                    curveToRelative(0.9166f, 0f, 1.6666f, 0.7079f, 1.6666f, 1.573f)
                    // v3.7753
                    verticalLineToRelative(3.7753f)
                    // c0,0.0787 0.0834,0.1573 0.1667,0.1573
                    curveToRelative(0f, 0.0787f, 0.0834f, 0.1573f, 0.1667f, 0.1573f)
                    // h0.6667
                    horizontalLineToRelative(0.6667f)
                    // c0.5,0 0.8333,0.3146 0.8333,0.7865
                    curveToRelative(0.5f, 0f, 0.8333f, 0.3146f, 0.8333f, 0.7865f)
                    // c0,0.472 -0.3333,0.7079 -0.8333,0.7079
                    curveToRelative(0f, 0.472f, -0.3333f, 0.7079f, -0.8333f, 0.7079f)
                    close()
                    // M12.3,6
                    moveTo(12.3f, 6f)
                    // c0.6933,0 1.3,0.6067 1.3,1.3
                    curveToRelative(0.6933f, 0f, 1.3f, 0.6067f, 1.3f, 1.3f)
                    // s-0.52,1.3 -1.3,1.3
                    reflectiveCurveToRelative(-0.52f, 1.3f, -1.3f, 1.3f)
                    // S11,7.9933 11,7.3
                    reflectiveCurveTo(11f, 7.9933f, 11f, 7.3f)
                    // S11.6067,6 12.3,6
                    reflectiveCurveTo(11.6067f, 6f, 12.3f, 6f)
                    close()
                    // M12,2
                    moveTo(12f, 2f)
                    // C6.5,2 2,6.5 2,12
                    curveTo(6.5f, 2f, 2f, 6.5f, 2f, 12f)
                    // s4.5,10 10,10
                    reflectiveCurveToRelative(4.5f, 10f, 10f, 10f)
                    // s10,-4.5 10,-10
                    reflectiveCurveToRelative(10f, -4.5f, 10f, -10f)
                    // S17.5,2 12,2
                    reflectiveCurveTo(17.5f, 2f, 12f, 2f)
                }
            }.build()
        return _infoCircleIcon!!
    }
