package design.andromedacompose.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import design.andromedacompose.components.reveal.CircularReveal

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            var isLightTheme by remember { mutableStateOf(true) }

            CircularReveal(
                targetState = isLightTheme,
                animationSpec = tween(2500)
            ) { localTheme ->
                CatalogTheme(isLightTheme = localTheme) {
                    NavGraph(
                        onToggleTheme = {
                            isLightTheme = !isLightTheme
                        },
                    )
                }
            }
        }
    }
}
