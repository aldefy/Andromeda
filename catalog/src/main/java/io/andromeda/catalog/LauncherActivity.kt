package io.andromeda.catalog

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import io.andromeda.design.components.reveal.CircularReveal

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                var isLightTheme by remember { mutableStateOf(true) }

                CircularReveal(
                    targetState = isLightTheme,
                    animationSpec = tween(750)
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
}
