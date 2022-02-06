package io.andromeda.design.foundation.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import io.andromeda.design.foundation.typography.body.BodyModerateTypographyStyle
import io.andromeda.design.foundation.typography.body.BodySmallTypographyStyle
import io.andromeda.design.foundation.typography.caption.CaptionModerateBookTypographyStyle
import io.andromeda.design.foundation.typography.caption.CaptionModerateDemiTypographyStyle
import io.andromeda.design.foundation.typography.title.TitleHeroTypographyStyle
import io.andromeda.design.foundation.typography.title.TitleModerateBoldTypographyStyle
import io.andromeda.design.foundation.typography.title.TitleModerateDemiTypographyStyle
import io.andromeda.design.foundation.typography.title.TitleSmallDemiTypographyStyle

/**
 * Contains all the typography we provide for our components.
 * */
class AndromedaTypography(
    val titleHeroTextStyle: TextStyle,
    val titleModerateBoldTextStyle: TextStyle,
    val titleModerateDemiTextStyle: TextStyle,
    val titleSmallDemiTextStyle: TextStyle,
    val bodyModerateDefaultTypographyStyle: TextStyle,
    val bodySmallDefaultTypographyStyle: TextStyle,
    val captionModerateBookDefaultTypographyStyle: TextStyle,
    val captionModerateDemiDefaultTypographyStyle: TextStyle
)

/**
 * Builds the default typography set for our theme.
 * */
@Composable
fun textStyles(fontFamily: FontFamily): AndromedaTypography {
    return AndromedaTypography(
        titleHeroTextStyle = TitleHeroTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        titleModerateBoldTextStyle = TitleModerateBoldTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        titleModerateDemiTextStyle = TitleModerateDemiTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        titleSmallDemiTextStyle = TitleSmallDemiTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        bodyModerateDefaultTypographyStyle = BodyModerateTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        bodySmallDefaultTypographyStyle = BodySmallTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        captionModerateBookDefaultTypographyStyle = CaptionModerateBookTypographyStyle(fontFamily)
            .getComposeTextStyle(),
        captionModerateDemiDefaultTypographyStyle = CaptionModerateDemiTypographyStyle(fontFamily)
            .getComposeTextStyle()
    )
}

private fun toTextStyle(typographyStyle: BaseTypography): TextStyle {
    return TextStyle(
        fontSize = typographyStyle.fontSize,
        fontFamily = typographyStyle.fontFamily,
        lineHeight = typographyStyle.lineHeight,
        fontWeight = typographyStyle.fontWeight,
    )
}

fun BaseTypography.getComposeTextStyle(): TextStyle {
    return toTextStyle(this)
}

internal val LocalTypography = compositionLocalOf<AndromedaTypography> {
    error(
        "No typography provided! Make sure to wrap all usages of components in a " +
                "AndromedaTheme."
    )
}
