package design.andromedacompose.foundation.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import design.andromedacompose.foundation.typography.body.BodyModerateTypographyStyle
import design.andromedacompose.foundation.typography.body.BodySmallTypographyStyle
import design.andromedacompose.foundation.typography.caption.CaptionModerateBookTypographyStyle
import design.andromedacompose.foundation.typography.caption.CaptionModerateDemiTypographyStyle
import design.andromedacompose.foundation.typography.title.TitleHeroTypographyStyle
import design.andromedacompose.foundation.typography.title.TitleModerateBoldTypographyStyle
import design.andromedacompose.foundation.typography.title.TitleModerateDemiTypographyStyle
import design.andromedacompose.foundation.typography.title.TitleSmallDemiTypographyStyle

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
