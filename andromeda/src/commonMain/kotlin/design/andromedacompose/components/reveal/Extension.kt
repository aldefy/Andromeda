package design.andromedacompose.components.reveal

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset

/**
 * Author : Benjamin Monjoie
 * Credit : https://gist.github.com/bmonjoie/8506040b2ea534eac931378348622725
 */
fun Modifier.circularReveal(
    progress: Float,
    offset: Offset? = null,
) = then(
    clip(CircularRevealShape(progress, offset)),
)
