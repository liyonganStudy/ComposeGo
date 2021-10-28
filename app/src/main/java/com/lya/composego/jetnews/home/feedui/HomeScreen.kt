package com.lya.composego.jetnews.home.feedui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.lya.composego.R
import com.lya.composego.jetnews.home.PostCardHistory
import com.lya.composego.jetnews.home.PostCardPopular
import com.lya.composego.jetnews.home.PostCardSimple
import com.lya.composego.jetnews.home.PostCardTop
import com.lya.composego.jetnews.home.data.FakePostsRepository
import com.lya.composego.jetnews.home.model.HomeUiState
import com.lya.composego.jetnews.home.model.HomeViewModel
import com.lya.composego.jetnews.home.model.Post
import com.lya.composego.jetnews.ui.JetnewsDestinations
import com.lya.composego.jetnews.ui.JetnewsTopAppBar

@Composable
fun HomeScreen(
    openDrawer: () -> Unit,
    uiState: HomeUiState,
    homeViewModel: HomeViewModel
) {

    Scaffold(topBar = {
        JetnewsTopAppBar(JetnewsDestinations.HOME_ROUTE, openDrawer, 4.dp)
    }) {
        val contentModifier = Modifier.padding(it)
        LoadingContent(
            empty = when (uiState) {
                is HomeUiState.HasPosts -> false
                is HomeUiState.NoPosts -> uiState.isLoading
            },
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            onRefresh = { homeViewModel.refreshPosts() },
            content = {
                when (uiState) {
                    is HomeUiState.HasPosts -> HasPostsContent(
                        uiState,
                        contentModifier,
                        onArticleTapped = { postId ->
                            homeViewModel.interactedWithArticleDetails(postId)
                        },
                        onToggleFavorite = { postId ->
                            homeViewModel.toggleFavourite(postId)
                        }
                    )
                    is HomeUiState.NoPosts -> {
                        if (uiState.errorMessages.isEmpty()) {
                            // if there are no posts, and no error, let the user refresh manually
                            TextButton(
                                onClick = { homeViewModel.refreshPosts() },
                                contentModifier.fillMaxSize()
                            ) {
                                Text(
                                    stringResource(id = R.string.home_tap_to_load_content),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            // there's currently an error showing, don't show any content
                            Box(contentModifier.fillMaxSize()) { /* empty screen */ }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun HasPostsContent(
    uiState: HomeUiState.HasPosts,
    modifier: Modifier,
    onArticleTapped: (postId: String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        val postsFeed = uiState.postsFeed
        item { PostListTopSection(postsFeed.highlightedPost, onArticleTapped) }

        if (postsFeed.recommendedPosts.isNotEmpty()) {
            item {
                PostListSimpleSection(
                    postsFeed.recommendedPosts,
                    onArticleTapped,
                    uiState.favorites,
                    onToggleFavorite
                )
            }
        }

        if (postsFeed.popularPosts.isNotEmpty()) {
            item { PostListPopularSection(postsFeed.popularPosts, onArticleTapped) }
        }

        if (postsFeed.recentPosts.isNotEmpty()) {
            item { PostListHistorySection(postsFeed.recentPosts, onArticleTapped) }
        }
    }
}

@Composable
private fun PostListHistorySection(
    posts: List<Post>,
    navigateToArticle: (String) -> Unit
) {
    Column {
        posts.forEach { post ->
            PostCardHistory(post, navigateToArticle)
            PostListDivider()
        }
    }
}

@Composable
private fun PostListPopularSection(
    posts: List<Post>,
    navigateToArticle: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.home_popular_section_title),
            style = MaterialTheme.typography.subtitle1
        )

        LazyRow(modifier = Modifier.padding(end = 16.dp)) {
            items(posts) { post ->
                PostCardPopular(
                    post,
                    navigateToArticle,
                    Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
            }
        }
        PostListDivider()
    }
}

@Composable
private fun PostListSimpleSection(
    posts: List<Post>,
    navigateToArticle: (String) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit
) {
    Column {
        posts.forEach { post ->
            PostCardSimple(
                post = post,
                navigateToArticle = navigateToArticle,
                isFavorite = favorites.contains(post.id),
                onToggleFavorite = { onToggleFavorite(post.id) }
            )
            PostListDivider()
        }
    }
}

@Composable
private fun PostListTopSection(post: Post, navigateToArticle: (String) -> Unit) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.home_top_section_title),
        style = MaterialTheme.typography.subtitle1
    )
    PostCardTop(
        post = post,
        modifier = Modifier.clickable(onClick = { navigateToArticle(post.id) })
    )
    PostListDivider()
}

@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val homeViewModel = HomeViewModel(FakePostsRepository())
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(openDrawer = {}, uiState, homeViewModel)
}