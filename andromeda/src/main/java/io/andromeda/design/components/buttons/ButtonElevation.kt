package io.andromeda.design.components.buttons

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.collect

@Stable
interface ButtonElevation {
    /**
     * Contract to help build Button Elevation based on button's [enabled] and [interactionSource].
     *
     * @param enabled - to decide if button is enabled.
     * @param interactionSource - [InteractionSource] for the given button.
     */
    @Composable
    fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp>
}

private val DefaultIncomingSpec = TweenSpec<Dp>(
    durationMillis = 120,
    easing = FastOutSlowInEasing
)

private val DefaultOutgoingSpec = TweenSpec<Dp>(
    durationMillis = 150,
    easing = CubicBezierEasing(0.40f, 0.00f, 0.60f, 1.00f)
)

@Suppress("unused")
private val HoveredOutgoingSpec = TweenSpec<Dp>(
    durationMillis = 120,
    easing = CubicBezierEasing(0.40f, 0.00f, 0.60f, 1.00f)
)

@Stable
internal class DefaultButtonElevation(
    private val defaultElevation: Dp,
    private val pressedElevation: Dp,
    private val disabledElevation: Dp,
) : ButtonElevation {
    @Composable
    override fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp> {
        val interactions = remember { mutableStateListOf<Interaction>() }
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        interactions.add(interaction)
                    }
                    is PressInteraction.Release -> {
                        interactions.remove(interaction.press)
                    }
                    is PressInteraction.Cancel -> {
                        interactions.remove(interaction.press)
                    }
                }
            }
        }

        val interaction = interactions.lastOrNull()
        val target = if (enabled) {
            when (interaction) {
                is PressInteraction.Press -> pressedElevation
                else -> defaultElevation
            }
        } else {
            disabledElevation
        }
        val animatable = remember { Animatable(target, Dp.VectorConverter) }
        if (enabled) {
            LaunchedEffect(key1 = target) {
                val lastInteraction = when (animatable.targetValue) {
                    pressedElevation -> PressInteraction.Press(Offset.Zero)
                    else -> null
                }
            }
        } else {
            LaunchedEffect(key1 = target) {
                animatable.snapTo(target)
            }
        }

        return animatable.asState()
    }
}

internal suspend fun Animatable<Dp, *>.animateElevation(
    target: Dp,
    from: Interaction? = null,
    to: Interaction? = null
) {
    val spec = when {
        // Moving to a new state
        to != null -> ElevationDefaults.incomingAnimationSpecForInteraction(to)
        // Moving to default, from a previous state
        from != null -> ElevationDefaults.outgoingAnimationSpecForInteraction(from)
        // Loading the initial state, or moving back to the baseline state from a disabled /
        // unknown state, so just snap to the final value.
        else -> null
    }
    if (spec != null) animateTo(target, spec) else snapTo(target)
}

/**
 * Contains default [AnimationSpec]s used for animating elevation between different [Interaction]s.
 *
 * Typically you should use [animateElevation] instead, which uses these [AnimationSpec]s
 * internally. [animateElevation] in turn is used by the defaults for [Button] and
 * [FloatingActionButton] - inside [ButtonDefaults.elevation] and
 * [FloatingActionButtonDefaults.elevation] respectively.
 *
 * @see animateElevation
 */
private object ElevationDefaults {
    /**
     * Returns the [AnimationSpec]s used when animating elevation to [interaction], either from a
     * previous [Interaction], or from the default state. If [interaction] is unknown, then
     * returns `null`.
     *
     * @param interaction the [Interaction] that is being animated to
     */
    fun incomingAnimationSpecForInteraction(interaction: Interaction): AnimationSpec<Dp>? {
        return when (interaction) {
            is PressInteraction.Press -> DefaultIncomingSpec
            is DragInteraction.Start -> DefaultIncomingSpec
            else -> null
        }
    }

    /**
     * Returns the [AnimationSpec]s used when animating elevation away from [interaction], to the
     * default state. If [interaction] is unknown, then returns `null`.
     *
     * @param interaction the [Interaction] that is being animated away from
     */
    fun outgoingAnimationSpecForInteraction(interaction: Interaction): AnimationSpec<Dp>? {
        return when (interaction) {
            is PressInteraction.Press -> DefaultOutgoingSpec
            is DragInteraction.Start -> DefaultOutgoingSpec
            // TODO: use [HoveredOutgoingSpec] when hovered
            else -> null
        }
    }
}
