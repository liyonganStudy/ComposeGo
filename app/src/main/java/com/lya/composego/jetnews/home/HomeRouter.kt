package com.lya.composego.jetnews.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lya.composego.jetnews.home.detailui.DetailScreen
import com.lya.composego.jetnews.home.feedui.HomeScreen
import com.lya.composego.jetnews.home.model.HomeUiState
import com.lya.composego.jetnews.home.model.HomeViewModel


@Composable
fun HomeRouter(
    openDrawer: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsState()

    when (getHomeScreenType(uiState)) {
        HomeScreenType.Feed -> {
            HomeScreen(openDrawer, uiState, homeViewModel)
        }
        HomeScreenType.ArticleDetails -> {
            DetailScreen(openDrawer, uiState, homeViewModel)

            BackHandler {
                homeViewModel.interactedWithFeed()
            }
        }
    }
}

private enum class HomeScreenType {
    Feed,
    ArticleDetails
}

private fun getHomeScreenType(uiState: HomeUiState): HomeScreenType {
    return when (uiState) {
        is HomeUiState.HasPosts -> {
            if (uiState.isArticleOpen) {
                HomeScreenType.ArticleDetails
            } else {
                HomeScreenType.Feed
            }
        }
        is HomeUiState.NoPosts -> HomeScreenType.Feed
    }
}