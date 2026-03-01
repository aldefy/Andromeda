package design.andromedacompose.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import design.andromedacompose.foundation.typography.AndromedaFonts
import design.andromedacompose.foundation.typography.BaseTypography

/**
 * Title Large typography style
 */
class TitleLargeTypographyStyle : BaseTypography {
    override val fontFamily: FontFamily = AndromedaFonts

    override val fontSize: TextUnit = 21.sp

    override val fontWeight: FontWeight = FontWeight.Bold

    override val lineHeight: TextUnit = 28.sp
}
