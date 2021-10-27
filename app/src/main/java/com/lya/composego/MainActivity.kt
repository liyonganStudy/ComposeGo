@file:Suppress("FunctionName")

package com.lya.composego

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lya.composego.ui.theme.ComposeGoTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
private fun MyApp() {
    val shouldShowOnboarding = remember { mutableStateOf(true) }
    if (shouldShowOnboarding.value) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding.value = false})
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(names.size) { index ->
            Greeting(names[index])
        }
    }
}

@Composable
private fun OnboardingScreen(onContinueClicked: () -> Unit) {
    val context = LocalContext.current

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }

            Button(modifier = Modifier.padding(vertical = 24.dp), onClick = {
                context.startActivity(Intent(context, LayoutsActivity::class.java))
            }) {
                Text("Launch layouts activity")
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp).padding(bottom = extraPadding)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeGoTheme {
        MyApp()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {

    ComposeGoTheme {
        OnboardingScreen(onContinueClicked = {  })
    }
}