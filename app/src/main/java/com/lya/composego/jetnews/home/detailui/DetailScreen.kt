package com.lya.composego.jetnews.home.detailui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.lya.composego.jetnews.home.model.HomeUiState
import com.lya.composego.jetnews.home.model.HomeViewModel

@Composable
fun DetailScreen(openDrawer: () -> Unit,
                 uiState: HomeUiState,
                 homeViewModel: HomeViewModel
) {
    Text("detail")
}