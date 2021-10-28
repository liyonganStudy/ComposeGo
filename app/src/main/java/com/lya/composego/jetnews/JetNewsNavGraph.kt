package com.lya.composego.jetnews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object JetnewsDestinations {
    const val HOME_ROUTE = "home"
    const val INTERESTS_ROUTE = "interests"
}

interface JetnewsNavigationActions {

    fun navigateToHome()

    fun navigateToInterests()

}

@Composable
fun JetnewsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = JetnewsDestinations.HOME_ROUTE
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(JetnewsDestinations.HOME_ROUTE) {
            HomeScreen(openDrawer)
        }

        composable(JetnewsDestinations.INTERESTS_ROUTE) {
            InterestScreen(openDrawer)
        }
    }

}

class JetNewsNavigationActionsImpl(private val navController: NavHostController) :
    JetnewsNavigationActions {
    override fun navigateToHome() {
        navController.navigate(JetnewsDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateToInterests() {
        navController.navigate(JetnewsDestinations.INTERESTS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}