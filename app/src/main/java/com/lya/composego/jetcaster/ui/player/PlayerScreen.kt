package com.lya.composego.jetcaster.ui.player

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.systemBarsPadding
import com.lya.composego.R
import com.lya.composego.jetnews.util.*
import java.time.Duration

@Composable
fun PlayerScreen(
    onBackClick: () -> Unit
) {
    val uiState by remember {
        mutableStateOf(PlayerUiState(
            "A Threat to China’s Economy",
            null,
            "The Daily",
            "https://image.simplecastcdn.com/images/03d8b493-87fc-4bd1-931f-8a8e9b945d8a/2cce5659-f647-4366-b318-46e4b67afcfa/3000x3000/c81936f538106550b804e7e4fe2c236319bab7fba37941a6e8f7e5c3d3048b88fc5b2182fb790f7d446bdc820406456c94287f245db89d8656c105d5511ec3de.jpeg?aid=rss_feed"
        ))
    }
    if (uiState.podcastName.isNotEmpty()) {
        PlayerDynamicTheme(uiState.podcastImageUrl) {
            val color = LocalContentColor.current
            Log.i("lya", "color ${color == Color.White} PlayerScreen")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalGradientScrim(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
                    .systemBarsPadding()
                    .padding(horizontal = 8.dp)
            ) {
                TopAppBar(onBackPress = onBackClick)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    PlayerImage(
                        podcastImageUrl = uiState.podcastImageUrl,
                        modifier = Modifier.weight(10f)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    PodcastDescription(uiState.title, uiState.podcastName)
                    Spacer(modifier = Modifier.height(32.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(10f)
                    ) {
                        PlayerSlider(uiState.duration)
                        PlayerButtons(Modifier.padding(vertical = 8.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {  },
                        modifier = Modifier.align(
                            BiasAlignment.Horizontal(0f)
                        )
                    ) {
                        Text("button")
                    }
                }
            }
        }
    } else {
        FullScreenLoading()
    }

}

@Composable
private fun TopAppBar(onBackPress: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = onBackPress) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.cd_back)
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.PlaylistAdd,
                contentDescription = stringResource(R.string.cd_add)
            )
        }
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.cd_more)
            )
        }
    }
}

@Composable
private fun PlayerDynamicTheme(
    podcastImageUrl: String,
    content: @Composable () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val dominantColorState = rememberDominantColorState(
        defaultColor = MaterialTheme.colors.surface
    ) { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        // Update the dominantColorState with colors coming from the podcast image URL
        LaunchedEffect(podcastImageUrl) {
            if (podcastImageUrl.isNotEmpty()) {
                dominantColorState.updateColorsFromImageUrl(podcastImageUrl)
            } else {
                dominantColorState.reset()
            }
        }
        content()
    }
}


@Composable
private fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PlayerImage(
    podcastImageUrl: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = podcastImageUrl,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
private fun PodcastDescription(
    title: String,
    podcastName: String,
    titleTextStyle: TextStyle = MaterialTheme.typography.h5
) {
    Text(
        text = title,
        style = titleTextStyle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = podcastName,
            style = MaterialTheme.typography.body2,
            maxLines = 1
        )
    }
}

@Composable
private fun PlayerSlider(episodeDuration: Duration?) {
    if (episodeDuration != null) {
        Column(Modifier.fillMaxWidth()) {
            Slider(value = 0f, onValueChange = { })
            Row(Modifier.fillMaxWidth()) {
                Text(text = "0s")
                Spacer(modifier = Modifier.weight(1f))
                Text("${episodeDuration.seconds}s")
            }
        }
    }
}

@Composable
private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = stringResource(R.string.cd_skip_previous),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Filled.Replay10,
            contentDescription = stringResource(R.string.cd_reply10),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Rounded.PlayCircleFilled,
            contentDescription = stringResource(R.string.cd_play),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
        )
        Image(
            imageVector = Icons.Filled.Forward30,
            contentDescription = stringResource(R.string.cd_forward30),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = stringResource(R.string.cd_skip_next),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = buttonsModifier
        )
    }
}