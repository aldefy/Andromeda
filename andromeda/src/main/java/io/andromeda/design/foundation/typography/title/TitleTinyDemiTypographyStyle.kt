package io.andromeda.design.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.andromeda.design.foundation.typography.AndromedaFonts
import io.andromeda.design.foundation.typography.BaseTypography

/**
 * Title Tiny Demi typography style
 */
class TitleTinyDemiTypographyStyle : BaseTypography {
    override val fontFamily: FontFamily = AndromedaFonts

    override val fontSize: TextUnit = 14.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 20.sp
}
