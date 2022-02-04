package io.andromeda.design.foundation.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.andromeda.design.foundation.typography.BaseTypography

/**
 * Title Hero typography style
 */
class TitleHeroTypographyStyle(fonts: FontFamily) : BaseTypography {
    override val fontFamily: FontFamily = fonts

    override val fontSize: TextUnit = 28.sp

    override val fontWeight: FontWeight = FontWeight.Bold

    override val lineHeight: TextUnit = 44.sp
}
