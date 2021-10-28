package com.lya.composego.jetnews

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(openDrawer: () -> Unit) {
    Scaffold(topBar = {
        JetnewsTopAppBar(JetnewsDestinations.HOME_ROUTE, openDrawer, 4.dp)
    }) {
        Text("home")
    }
}