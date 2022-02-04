package io.andromeda.design.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.andromeda.design.foundation.typography.AndromedaFonts
import io.andromeda.design.foundation.typography.BaseTypography

/**
 * Title Extra Large typography style
 */
class TitleExtraLargeTypographyStyle : BaseTypography {
    override val fontFamily: FontFamily = AndromedaFonts

    override val fontSize: TextUnit = 24.sp

    override val fontWeight: FontWeight = FontWeight.Bold

    override val lineHeight: TextUnit = 36.sp
}
