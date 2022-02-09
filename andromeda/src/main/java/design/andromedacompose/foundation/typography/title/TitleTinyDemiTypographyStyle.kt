package design.andromedacompose.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import design.andromedacompose.foundation.typography.AndromedaFonts
import design.andromedacompose.foundation.typography.BaseTypography

/**
 * Title Tiny Demi typography style
 */
class TitleTinyDemiTypographyStyle : BaseTypography {
    override val fontFamily: FontFamily = AndromedaFonts

    override val fontSize: TextUnit = 14.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 20.sp
}
