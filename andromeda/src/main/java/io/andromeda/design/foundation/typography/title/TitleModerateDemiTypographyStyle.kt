package io.andromeda.design.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.andromeda.design.foundation.typography.BaseTypography

/**
 * Title Moderate Demi typography style
 */
class TitleModerateDemiTypographyStyle(fonts: FontFamily) : BaseTypography {
    override val fontFamily: FontFamily = fonts

    override val fontSize: TextUnit = 18.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 24.sp
}
