package com.lya.composego.jetnews

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object JetNewsDestinations {
    const val HOME_ROUTE = "home"
    const val INTERESTS_ROUTE = "interests"
}

interface JetnewsNavigationActions {

    fun navigateToHome()
    
    fun navigateToInterests()
    
}

class JetNewsNavigationActionsImpl(val navController: NavHostController) : JetnewsNavigationActions {
    override fun navigateToHome() {
        navController.navigate(JetNewsDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateToInterests() {
        navController.navigate(JetNewsDestinations.INTERESTS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}