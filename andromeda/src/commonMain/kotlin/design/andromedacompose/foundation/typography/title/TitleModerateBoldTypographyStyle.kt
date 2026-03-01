package design.andromedacompose.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import design.andromedacompose.foundation.typography.BaseTypography

/**
 * Title Moderate Bold typography style
 */
class TitleModerateBoldTypographyStyle(fonts: FontFamily) : BaseTypography {
    override val fontFamily: FontFamily = fonts

    override val fontSize: TextUnit = 18.sp

    override val fontWeight: FontWeight = FontWeight.Bold

    override val lineHeight: TextUnit = 24.sp
}
