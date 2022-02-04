package io.andromeda.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Suppress("unused")
object AndromedaIcons {

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

    public val LeftSelector: ImageVector
        @Composable
        get() = Icons.Default.KeyboardArrowLeft

    public val RightSelector: ImageVector
        @Composable
        get() = Icons.Default.KeyboardArrowRight
}
