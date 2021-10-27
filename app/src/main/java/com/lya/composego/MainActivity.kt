@file:Suppress("FunctionName")

package com.lya.composego

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lya.composego.ui.theme.ComposeGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                ComposeGoTheme {
                    val systemUiController = rememberSystemUiController()
                    val darkIcons = MaterialTheme.colors.isLight
                    SideEffect {
                        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
                    }

                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        MyApp()
                    }
                }
            }
        }
    }
}

@Composable
private fun MyApp() {
    Column(
        modifier = Modifier.statusBarsPadding().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        EntryItem(title = "JetNews", cls = LayoutsActivity::class.java)
    }
}

@Composable
private fun EntryItem(
    modifier: Modifier = Modifier,
    title: String,
    cls: Class<*>
) {
    val context = LocalContext.current
    Button(
        onClick = {
            context.startActivity(Intent(context, cls))
        },
        modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(title)
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeGoTheme {
        MyApp()
    }
}
