package com.lya.composego.jetcaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.lya.composego.jetcaster.ui.JetcasterApp
import com.lya.composego.jetnews.JetNewsApp
import com.lya.composego.ui.theme.ComposeGoTheme

class JetcasterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetcasterApp()
        }
    }

}


