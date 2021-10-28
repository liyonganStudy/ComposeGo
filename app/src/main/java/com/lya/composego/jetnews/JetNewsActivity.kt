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
            val scaffoldState = rememberScaffoldState()

            Scaffold(
                drawerContent = {
                    JetNewsDrawer(
                        statusBarPaddingModifier,
                        currentRoute,
                        { coroutineScope.launch { scaffoldState.drawerState.close() } },
                        navigationActions
                    )
                },
                scaffoldState = scaffoldState
            ) {
                JetnewsNavGraph(navController = naviController)
            }
        }
    }
}

@Preview
@Composable
fun JetNewsAppPreview() {
    JetNewsApp()
}
