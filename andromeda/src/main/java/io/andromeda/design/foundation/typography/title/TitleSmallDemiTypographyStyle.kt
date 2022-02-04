package io.andromeda.design.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.andromeda.design.foundation.typography.BaseTypography

/**
 * Title Small Demi typography style
 */
class TitleSmallDemiTypographyStyle(fonts: FontFamily) : BaseTypography {
    override val fontFamily: FontFamily = fonts

    override val fontSize: TextUnit = 16.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 20.sp
}
