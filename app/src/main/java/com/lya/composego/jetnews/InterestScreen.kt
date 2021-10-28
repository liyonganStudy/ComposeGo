package com.lya.composego.jetnews

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun InterestScreen(openDrawer: () -> Unit) {
    Scaffold(topBar = {
        JetnewsTopAppBar(JetnewsDestinations.INTERESTS_ROUTE, openDrawer)
    }) {
        Text("interest")
    }
}