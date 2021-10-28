package com.lya.composego.jetnews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lya.composego.R

@Composable
fun JetNewsDrawer(
    modifier: Modifier = Modifier,
    currentRoute: String,
    closeDrawer: () -> Unit,
    navigationActions: JetnewsNavigationActions
) {
    Column(modifier.fillMaxSize()) {
        Logo(Modifier.padding(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

        DrawerItem(
            Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
            currentRoute == JetnewsDestinations.HOME_ROUTE,
            {
                navigationActions.navigateToHome()
                closeDrawer()
            },
            Icons.Filled.Home,
            stringResource(id = R.string.home_title)
        )

        DrawerItem(
            Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
            currentRoute == JetnewsDestinations.INTERESTS_ROUTE,
            {
                navigationActions.navigateToInterests()
                closeDrawer()
            },
            Icons.Filled.ListAlt,
            stringResource(id = R.string.interests_title)
        )
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Row(modifier) {
        Image(
            painterResource(R.drawable.ic_jetnews_logo),
            null,
            colorFilter = ColorFilter.tint(
                MaterialTheme.colors.primary
            )
        )
        Spacer(Modifier.width(8.dp))
        Image(
            painterResource(R.drawable.ic_jetnews_wordmark),
            null,
            colorFilter = ColorFilter.tint(
                MaterialTheme.colors.onSurface
            )
        )
    }
}

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    action: () -> Unit,
    icon: ImageVector,
    title: String
) {
    val colors = MaterialTheme.colors
    val bgColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }
    val contentColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    Surface(
        modifier.fillMaxWidth(),
        color = bgColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(imageVector = icon, null, colorFilter = ColorFilter.tint(contentColor))
                Text(title, color = contentColor)
            }
        }
    }
}