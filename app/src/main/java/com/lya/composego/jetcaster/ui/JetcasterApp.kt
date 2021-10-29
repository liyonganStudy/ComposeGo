package com.lya.composego.jetcaster.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lya.composego.jetcaster.ui.theme.JetcasterTheme
import com.lya.composego.ui.theme.ComposeGoTheme

@Composable
fun JetcasterApp() {
    JetcasterTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }
            val naviController = rememberNavController()
            val navigationActions = remember(naviController) { JetcasterNavigationActionsImpl(naviController) }
            JetcasterNavGraph(navigationActions, naviController)
        }
    }
}

@Preview
@Composable
fun JetcasterAppPreview() {
    JetcasterApp()
}