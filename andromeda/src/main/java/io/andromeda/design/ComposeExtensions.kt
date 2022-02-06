package io.andromeda.design

import android.os.SystemClock
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.graphics.ColorUtils
import androidx.compose.ui.graphics.Color as ComposeColor

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun LazyListState.isScrolledToTheStart() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == 0

/**
 * Wraps an [onClick] lambda with another one that supports debouncing. The default deboucing time
 * is 1000ms.
 *
 * @return debounced onClick
 */
@Composable
inline fun debounced(crossinline onClick: () -> Unit, debounceTime: Long = 1000L): () -> Unit {
    var lastTimeClicked by remember { mutableStateOf(0L) }
    val onClickLambda: () -> Unit = {
        val now = SystemClock.uptimeMillis()
        if (now - lastTimeClicked > debounceTime) {
            onClick()
        }
        lastTimeClicked = now
    }
    return onClickLambda
}

/**
 * The same as [Modifier.clickable] with support to debouncing.
 */
fun Modifier.debouncedClickable(
    debounceTime: Long = 1000L,
    onClick: () -> Unit
): Modifier {
    return this.composed {
        val clickable = debounced(debounceTime = debounceTime, onClick = { onClick() })
        this.clickable { clickable() }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun ComposeColor.invert(): ComposeColor {
    val hsl = floatArrayOf(0f, 0f, 0f)
    ColorUtils.colorToHSL((value shr 32).toInt(), hsl)
    hsl[2] = 1 - hsl[2]
    val colorInt = ColorUtils.HSLToColor(hsl)
    return ComposeColor(colorInt)
}
