package design.andromedacompose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.foundation.colors.defaultDarkColors
import design.andromedacompose.foundation.colors.defaultLightColors

@Composable
fun CatalogTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    AndromedaTheme(
        colors = if (isLightTheme) defaultLightColors() else defaultDarkColors(),
        fontFamily = CatalogAppFonts
    ) {
        content()
    }
}

val CatalogAppFonts = FontFamily(
    Font(R.font.catalog_black, FontWeight.Black),
    Font(R.font.catalog_bold, FontWeight.Bold),
    Font(R.font.catalog_extrabold, FontWeight.ExtraBold),
    Font(R.font.catalog_extralight, FontWeight.Light),
    Font(R.font.catalog_medium, FontWeight.Medium),
    Font(R.font.catalog_regular, FontWeight.W400),
    Font(R.font.catalog_semibold, FontWeight.SemiBold),
    Font(R.font.catalog_thin, FontWeight.Thin)
)
