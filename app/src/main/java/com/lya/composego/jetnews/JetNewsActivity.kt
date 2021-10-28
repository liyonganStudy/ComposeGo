package com.lya.composego.jetnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lya.composego.jetnews.ui.JetNewsDrawer
import com.lya.composego.jetnews.ui.JetNewsNavigationActionsImpl
import com.lya.composego.jetnews.ui.JetnewsDestinations
import com.lya.composego.jetnews.ui.JetnewsNavGraph
import com.lya.composego.ui.theme.ComposeGoTheme
import kotlinx.coroutines.launch

class JetNewsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetNewsApp()
        }
    }

}

@Composable
fun JetNewsApp() {
    ProvideWindowInsets {
        ComposeGoTheme {
            val systemUiController = rememberSystemUiController()
            val isLight = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, isLight)
            }
            val statusBarPaddingModifier = Modifier.statusBarsPadding()
            val naviController = rememberNavController()
            val navigationActions = remember(naviController) { JetNewsNavigationActionsImpl(naviController) }
            val navBackStackEntry by naviController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: JetnewsDestinations.HOME_ROUTE

            val coroutineScope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            ModalDrawer(
                drawerContent = {
                    JetNewsDrawer(
                        statusBarPaddingModifier,
                        currentRoute,
                        { coroutineScope.launch { drawerState.close() } },
                        navigationActions
                    )
                },
                drawerState = drawerState
            ) {
                JetnewsNavGraph(
                    modifier = statusBarPaddingModifier,
                    navController = naviController,
                    openDrawer = { coroutineScope.launch { drawerState.open() } }
                )
            }
        }
    }
}

@Preview
@Composable
fun JetNewsAppPreview() {
    JetNewsApp()
}
