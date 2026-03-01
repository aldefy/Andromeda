package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _alertIcon: ImageVector? = null
val AlertIcon: ImageVector
    get() {
        if (_alertIcon != null) return _alertIcon!!
        _alertIcon =
            ImageVector.Builder(
                name = "AlertIcon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    // M13.6086,3.247
                    moveTo(13.6086f, 3.247f)
                    // l8.1916,15.8
                    lineToRelative(8.1916f, 15.8f)
                    // c0.0999,0.2 0.1998,0.5 0.1998,0.8
                    curveToRelative(0.0999f, 0.2f, 0.1998f, 0.5f, 0.1998f, 0.8f)
                    // c0,1 -0.7992,1.8 -1.7982,1.8
                    curveToRelative(0f, 1f, -0.7992f, 1.8f, -1.7982f, 1.8f)
                    // L3.7188,21.647
                    lineTo(3.7188f, 21.647f)
                    // c-0.2997,0 -0.4995,-0.1 -0.7992,-0.2
                    curveToRelative(-0.2997f, 0f, -0.4995f, -0.1f, -0.7992f, -0.2f)
                    // c-0.7992,-0.5 -1.1988,-1.5 -0.6993,-2.4
                    curveToRelative(-0.7992f, -0.5f, -1.1988f, -1.5f, -0.6993f, -2.4f)
                    // c5.3067,-10.1184 8.0706,-15.385 8.2915,-15.8
                    curveToRelative(5.3067f, -10.1184f, 8.0706f, -15.385f, 8.2915f, -15.8f)
                    // c0.3314,-0.6222 0.8681,-0.8886 1.4817,-0.897
                    curveToRelative(0.3314f, -0.6222f, 0.8681f, -0.8886f, 1.4817f, -0.897f)
                    // c0.6135,-0.008 1.273,0.2807 1.6151,0.897
                    curveToRelative(0.6135f, -0.008f, 1.273f, 0.2807f, 1.6151f, 0.897f)
                    close()
                    // M12,18.95
                    moveTo(12f, 18.95f)
                    // c0.718,0 1.3,-0.582 1.3,-1.3
                    curveToRelative(0.718f, 0f, 1.3f, -0.582f, 1.3f, -1.3f)
                    // c0,-0.718 -0.582,-1.3 -1.3,-1.3
                    curveToRelative(0f, -0.718f, -0.582f, -1.3f, -1.3f, -1.3f)
                    // c-0.718,0 -1.3,0.582 -1.3,1.3
                    curveToRelative(-0.718f, 0f, -1.3f, 0.582f, -1.3f, 1.3f)
                    // c0,0.718 0.582,1.3 1.3,1.3
                    curveToRelative(0f, 0.718f, 0.582f, 1.3f, 1.3f, 1.3f)
                    close()
                    // M11.1105,8.747
                    moveTo(11.1105f, 8.747f)
                    // v5.4
                    verticalLineToRelative(5.4f)
                    // c0,0.5 0.4,0.9 0.9,0.9
                    curveToRelative(0f, 0.5f, 0.4f, 0.9f, 0.9f, 0.9f)
                    // s0.9,-0.4 0.9,-0.9
                    reflectiveCurveToRelative(0.9f, -0.4f, 0.9f, -0.9f)
                    // v-5.3
                    verticalLineToRelative(-5.3f)
                    // c0,-0.5 -0.4,-0.9 -0.9,-0.9
                    curveToRelative(0f, -0.5f, -0.4f, -0.9f, -0.9f, -0.9f)
                    // s-0.9,0.4 -0.9,0.8
                    reflectiveCurveToRelative(-0.9f, 0.4f, -0.9f, 0.8f)
                    close()
                }
            }.build()
        return _alertIcon!!
    }
