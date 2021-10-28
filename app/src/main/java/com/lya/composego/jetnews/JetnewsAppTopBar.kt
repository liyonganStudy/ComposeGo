package com.lya.composego.jetnews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lya.composego.R

@Composable
fun JetnewsTopAppBar(
    currentRouter: String,
    openDrawer: () -> Unit,
    elevation: Dp = 0.dp
) {
    val title = stringResource(id = R.string.app_name)
    TopAppBar(
        title = {
            if (currentRouter == JetnewsDestinations.HOME_ROUTE) {
                Icon(
                    painter = painterResource(R.drawable.ic_jetnews_wordmark),
                    contentDescription = title,
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 4.dp, top = 10.dp)
                )
            } else {
                Text(
                    text = stringResource(R.string.cd_interests),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    painter = painterResource(R.drawable.ic_jetnews_logo),
                    contentDescription = stringResource(R.string.cd_open_navigation_drawer),
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO: Open search */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.cd_search)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}