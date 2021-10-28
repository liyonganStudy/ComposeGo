package com.lya.composego.jetnews.interest

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.lya.composego.jetnews.ui.JetnewsDestinations
import com.lya.composego.jetnews.ui.JetnewsTopAppBar

@Composable
fun InterestScreen(openDrawer: () -> Unit) {
    Scaffold(topBar = {
        JetnewsTopAppBar(JetnewsDestinations.INTERESTS_ROUTE, openDrawer)
    }) {
        Text("interest")
    }
}