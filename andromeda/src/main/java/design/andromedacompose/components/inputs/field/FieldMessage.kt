package design.andromedacompose.components.inputs.field

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Icon
import design.andromedacompose.foundation.size
import design.andromedacompose.foundation.typography.ProvideMergedTextStyle

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun FieldMessage(
    toShow: Boolean = true,
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
) {
    val state = when {
        error != null -> Message.Error(error)
        info != null -> Message.Info(info)
        else -> null
    }
    if (toShow)
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                if (targetState == null || initialState == null) {
                    val enter = slideInVertically(animationSpec = tween(AnimationDuration)) +
                        fadeIn(animationSpec = tween(AnimationDuration))
                    val exit = slideOutVertically(animationSpec = tween(AnimationDuration)) +
                        fadeOut(animationSpec = tween(AnimationDuration))
                    val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                    enter with exit using size
                } else {
                    val enter = fadeIn(animationSpec = tween(AnimationDuration))
                    val exit = fadeOut(animationSpec = tween(AnimationDuration))
                    val size = SizeTransform(clip = false) { _, _ -> tween(AnimationDuration) }
                    enter with exit using size
                }
            },
        ) { message ->
            if (message != null) {
                Row(
                    Modifier.padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val icon = when (message) {
                        is Message.Error -> Icons.Filled.Error
                        is Message.Info -> Icons.Filled.Info
                    }
                    val tintColor = when (message) {
                        is Message.Error -> AndromedaTheme.colors.secondaryColors.error
                        is Message.Info -> AndromedaTheme.colors.tertiaryColors.active
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(height = 20.sp, width = 16.sp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.sp),
                            tint = tintColor,
                        )
                    }
                    ProvideMergedTextStyle(
                        AndromedaTheme.typography.bodyModerateDefaultTypographyStyle.copy(
                            color = tintColor
                        )
                    ) {
                        message.content.invoke()
                    }
                }
            } else {
                Box {}
            }
        }
}

private sealed class Message(
    open val content: @Composable (() -> Unit),
) {
    data class Error(override val content: @Composable () -> Unit) : Message(content)
    data class Info(override val content: @Composable () -> Unit) : Message(content)
}

private const val AnimationDuration = 150
