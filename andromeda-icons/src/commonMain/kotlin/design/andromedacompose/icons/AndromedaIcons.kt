package design.andromedacompose.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.adamglin.PhosphorIcons

@Suppress("unused")
object AndromedaIcons {
    val Visibility: ImageVector get() = VisibilityOnIcon
    val VisibilityOff: ImageVector get() = VisibilityOffIcon
    val InformationCircle: ImageVector get() = InfoCircleIcon
    val Password: ImageVector get() = PasswordIcon
    val Alert: ImageVector get() = AlertIcon
    val AlertCircle: ImageVector get() = AlertCircleIcon
    val Photos: ImageVector get() = PhotosIcon

    /** Pre-built system icons (ArrowBack, Close, Error, Info, Edit, MoreVert) */
    val System: AndromedaSystemIcons = AndromedaSystemIcons

    /**
     * Phosphor Icons — 1500+ icons in 6 weights (Bold, Duotone, Fill, Light, Regular, Thin).
     *
     * Usage: `AndromedaIcons.Phosphor.Regular.Heart`
     */
    val Phosphor: PhosphorIcons = PhosphorIcons
}
