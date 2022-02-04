package io.andromeda.catalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import io.andromeda.design.AndromedaTheme

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndromedaTheme {
                LazyColumn {
                    item {
                        Text(
                            text = "Colors",
                            style = AndromedaTheme.typography.titleModerateBoldTextStyle
                        )
                    }
                    item {
                        Text(
                            text = "Shapes",
                            style = AndromedaTheme.typography.titleModerateBoldTextStyle
                        )
                    }
                    item {
                        Text(
                            text = "Typography",
                            style = AndromedaTheme.typography.titleModerateBoldTextStyle
                        )
                    }
                }
            }
        }
    }
}
