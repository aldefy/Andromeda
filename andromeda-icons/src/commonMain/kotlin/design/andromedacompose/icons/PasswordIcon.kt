package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _passwordIcon: ImageVector? = null
val PasswordIcon: ImageVector
    get() {
        if (_passwordIcon != null) return _passwordIcon!!
        _passwordIcon = ImageVector.Builder(
            name = "PasswordIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Path 1: Top bracket M22,16.4V20H2V16.4
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color(0xFF494A4A)),
                strokeLineWidth = 1.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(22f, 16.4f)
                verticalLineTo(20f)
                horizontalLineTo(2f)
                verticalLineTo(16.4f)
            }
            // Path 2: Bottom bracket M2,9.1V4H22V9.1
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color(0xFF494A4A)),
                strokeLineWidth = 1.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(2f, 9.1f)
                verticalLineTo(4f)
                horizontalLineTo(22f)
                verticalLineTo(9.1f)
            }
            // Path 3: Left dot circle
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color(0xFF494A4A)),
                strokeLineWidth = 1.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(6f, 13f)
                curveTo(6.5523f, 13f, 7f, 12.5523f, 7f, 12f)
                curveTo(7f, 11.4477f, 6.5523f, 11f, 6f, 11f)
                curveTo(5.4477f, 11f, 5f, 11.4477f, 5f, 12f)
                curveTo(5f, 12.5523f, 5.4477f, 13f, 6f, 13f)
                close()
            }
            // Path 4: Center dot circle
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color(0xFF494A4A)),
                strokeLineWidth = 1.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12f, 13f)
                curveTo(12.5523f, 13f, 13f, 12.5523f, 13f, 12f)
                curveTo(13f, 11.4477f, 12.5523f, 11f, 12f, 11f)
                curveTo(11.4477f, 11f, 11f, 11.4477f, 11f, 12f)
                curveTo(11f, 12.5523f, 11.4477f, 13f, 12f, 13f)
                close()
            }
            // Path 5: Right dot circle
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color(0xFF494A4A)),
                strokeLineWidth = 1.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(18f, 13f)
                curveTo(18.5523f, 13f, 19f, 12.5523f, 19f, 12f)
                curveTo(19f, 11.4477f, 18.5523f, 11f, 18f, 11f)
                curveTo(17.4477f, 11f, 17f, 11.4477f, 17f, 12f)
                curveTo(17f, 12.5523f, 17.4477f, 13f, 18f, 13f)
                close()
            }
        }.build()
        return _passwordIcon!!
    }
