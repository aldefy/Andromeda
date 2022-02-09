package design.andromedacompose.foundation.typography.body

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import design.andromedacompose.foundation.typography.BaseTypography

/**
 * Body Moderate Large typography style
 */
class BodyModerateTypographyStyle(fonts: FontFamily) : BaseTypography {
    override val fontFamily: FontFamily = fonts

    override val fontSize: TextUnit = 16.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 20.sp
}
