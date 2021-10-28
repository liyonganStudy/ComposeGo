package com.lya.composego.jetnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lya.composego.jetnews.home.HomeRouter
import com.lya.composego.jetnews.home.data.FakePostsRepository
import com.lya.composego.jetnews.home.model.HomeViewModel
import com.lya.composego.jetnews.interest.InterestScreen

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
            val homeViewModel = viewModel<HomeViewModel>(
                factory = HomeViewModel.provideFactory(FakePostsRepository())
            )
            HomeRouter(openDrawer, homeViewModel)
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