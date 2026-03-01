package design.andromedacompose.foundation.indication

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object AndromedaIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return AndromedaIndicationNode(interactionSource)
    }

    override fun equals(other: Any?) = other === this
    override fun hashCode() = System.identityHashCode(this)
}

private class AndromedaIndicationNode(
    private val interactionSource: InteractionSource,
) : Modifier.Node(), DrawModifierNode {
    private var isPressed = false

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        isPressed = true
                        invalidateDraw()
                    }
                    is PressInteraction.Release,
                    is PressInteraction.Cancel -> {
                        isPressed = false
                        invalidateDraw()
                    }
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        drawContent()
        if (isPressed) {
            drawRect(
                color = Color.Black,
                alpha = 0.1f,
            )
        }
    }
}

@Composable
fun rememberAndromedaIndication(): Indication = remember { AndromedaIndication }
