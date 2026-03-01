package design.andromedacompose.foundation.colors

import androidx.compose.ui.graphics.Color as ComposeColor

fun ComposeColor.Companion.parse(colorString: String): ComposeColor {
    val color = colorString.removePrefix("#")
    val colorLong = color.toLong(16)
    return when (color.length) {
        6 ->
            ComposeColor(
                red = ((colorLong shr 16) and 0xFF).toInt(),
                green = ((colorLong shr 8) and 0xFF).toInt(),
                blue = (colorLong and 0xFF).toInt(),
            )
        8 ->
            ComposeColor(
                alpha = ((colorLong shr 24) and 0xFF).toInt(),
                red = ((colorLong shr 16) and 0xFF).toInt(),
                green = ((colorLong shr 8) and 0xFF).toInt(),
                blue = (colorLong and 0xFF).toInt(),
            )
        else -> error("Invalid color string: $colorString")
    }
}
