package io.andromeda.design.foundation.typography.caption

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.andromeda.design.foundation.typography.BaseTypography

/**
 * Caption Moderate Demi typography style
 */
class CaptionModerateDemiTypographyStyle(fonts: FontFamily) : BaseTypography {
    override val fontFamily: FontFamily = fonts

    override val fontSize: TextUnit = 13.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 16.sp
}
