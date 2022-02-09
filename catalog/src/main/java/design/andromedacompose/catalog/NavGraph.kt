package design.andromedacompose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import design.andromedacompose.catalog.screens.ButtonScreen
import design.andromedacompose.catalog.screens.ColorsScreen
import design.andromedacompose.catalog.screens.IconsScreen
import design.andromedacompose.catalog.screens.IllustrationsScreen
import design.andromedacompose.catalog.screens.MainScreen
import design.andromedacompose.catalog.screens.NavBarScreen
import design.andromedacompose.catalog.screens.TypographyScreen

object MainDestinations {
    const val MAIN = "main"

    const val COLORS = "colors"
    const val ICONS = "icons"
    const val ILLUSTRATIONS = "illustrations"
    const val TYPOGRAPHY = "typography"

    const val BUTTON = "button"
    const val NAVBAR = "navbar"
}

@Composable
fun NavGraph(
    startDestination: String = MainDestinations.MAIN,
    onToggleTheme: () -> Unit,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.MAIN) {
            MainScreen(actions, onToggleTheme)
        }
        composable(MainDestinations.COLORS) {
            ColorsScreen(actions::navigateUp)
        }
        composable(MainDestinations.ICONS) {
            IconsScreen(actions::navigateUp)
        }
        composable(MainDestinations.ILLUSTRATIONS) {
            IllustrationsScreen(actions::navigateUp)
        }
        composable(MainDestinations.TYPOGRAPHY) {
            TypographyScreen(actions::navigateUp)
        }
        composable(MainDestinations.BUTTON) {
            ButtonScreen(actions::navigateUp)
        }
        composable(MainDestinations.NAVBAR) {
            NavBarScreen(actions::navigateUp)
        }
    }
}

data class MainActions(
    private val navController: NavHostController,
) {
    fun navigateUp() {
        navController.navigateUp()
    }

    fun showColors() {
        navController.navigate(MainDestinations.COLORS)
    }

    fun showIcons() {
        navController.navigate(MainDestinations.ICONS)
    }

    fun showIllustrations() {
        navController.navigate(MainDestinations.ILLUSTRATIONS)
    }

    fun showTypography() {
        navController.navigate(MainDestinations.TYPOGRAPHY)
    }

    fun showButton() {
        navController.navigate(MainDestinations.BUTTON)
    }

    fun showNavBar() {
        navController.navigate(MainDestinations.NAVBAR)
    }
}
