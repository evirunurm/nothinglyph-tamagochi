package com.evirunurm.nothinglyph.tamagochi.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.evirunurm.nothinglyph.tamagochi.presentation.screens.TamagochiScreen
import com.evirunurm.nothinglyph.ui.theme.NothingAndroidSDKDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NothingAndroidSDKDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TamagochiScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}