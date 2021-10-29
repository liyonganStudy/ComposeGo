package com.lya.composego.jetcaster.ui

import android.net.Uri
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.statusBarsPadding
import com.lya.composego.jetcaster.ui.home.HomeScreen
import com.lya.composego.jetcaster.ui.player.PlayerScreen
import com.lya.composego.jetcaster.ui.player.PlayerViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Player : Screen("player/{episodeUri}") {
        fun createRoute(episodeUri: String) = "player/$episodeUri"
    }
}

interface JetcasterNavigationActions {

    fun navigateToHome()

    fun navigateToPlayer(episodeUri: String, from: NavBackStackEntry)

    fun navBack()
}

class JetcasterNavigationActionsImpl(private val navController: NavHostController) :
    JetcasterNavigationActions {
    override fun navigateToHome() {
    }

    override fun navigateToPlayer(episodeUri: String, from: NavBackStackEntry) {
        if (from.lifecycle.currentState == Lifecycle.State.RESUMED) {
            val encodedUri = Uri.encode(episodeUri)
            navController.navigate(Screen.Player.createRoute(encodedUri))
        }
    }

    override fun navBack() {
        navController.popBackStack()
    }
}

@Composable
fun JetcasterNavGraph(
    navigationActions: JetcasterNavigationActions,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen() { url ->
                navigationActions.navigateToPlayer(url, it)
            }
        }

        composable(Screen.Player.route) { backStackEntry ->
//            val playerViewModel: PlayerViewModel = viewModel(
//                factory = PlayerViewModel.provideFactory(
//                    owner = backStackEntry,
//                    defaultArgs = backStackEntry.arguments
//                )
//            )
            Surface {
                PlayerScreen(
                    onBackClick = { navigationActions.navBack() },
                )
            }
        }

    }

}