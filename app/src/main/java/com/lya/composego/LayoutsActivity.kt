@file:Suppress("FunctionName")

package com.lya.composego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.lya.composego.ui.theme.ComposeGoTheme

class LayoutsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGoTheme {
                ParentLayout(
                    Modifier
                        .size(100.dp)
                        .padding(10.dp)
                        .background(Color.Blue)
                ) {
                    ChildLayout {
                        Box {}
                    }
                    ChildLayout {}
                }
            }
        }
    }
}

@Composable
private fun ParentLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    //布局的测量策略
    val measurePolicy = MeasurePolicy { measurables, constraints ->
        //1.测量 children
        val placeables = measurables.map { child ->
            child.measure(constraints)
        }

        var xPosition = 0
        //2.放置 children
        layout(constraints.minWidth, constraints.minHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(xPosition, 0)
                xPosition += placeable.width
            }
        }
    }
    //代码分析入口
    Layout(content = content, modifier = modifier, measurePolicy = measurePolicy)
}

@Composable
private fun ChildLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    //...代码和ParentLayout类似
}


@Composable
private fun LayoutsCodeLab(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotographerCardPreview() {
    ComposeGoTheme {
        LayoutsCodeLab()
    }
}
