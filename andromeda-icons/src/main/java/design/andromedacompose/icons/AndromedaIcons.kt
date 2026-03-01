package design.andromedacompose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.adamglin.PhosphorIcons

@Suppress("unused")
object AndromedaIcons {

    /**
     * Phosphor Icons — 1500+ icons in 6 weights (Bold, Duotone, Fill, Light, Regular, Thin).
     *
     * Usage: `AndromedaIcons.Phosphor.Regular.Heart`
     */
    val Phosphor: PhosphorIcons = PhosphorIcons
    val Visibility: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_icon_visibility_on)

    val VisibilityOff: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_icon_visibility_off)

    val InformationCircle: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_icon_info_circle)

    val Password: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_icon_password)

    public val Alert: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_icon_alert)

    public val AlertCircle: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_icon_alert_circle)

    public val Photos: Painter
        @Composable
        get() = painterResource(id = R.drawable.andromeda_icon_photos)
}
