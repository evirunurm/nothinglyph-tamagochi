package com.evirunurm.nothinglyph.tamagochi.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.evirunurm.nothinglyph.tamagochi.presentation.components.TamagochiGrid
import com.evirunurm.nothinglyph.tamagochi.presentation.viewmodel.TamagochiViewModel

/**
 * Main screen for the Tamagochi demo.
 */
@Composable
fun TamagochiScreen(
    modifier: Modifier = Modifier,
    viewModel: TamagochiViewModel = viewModel()
) {
    val size by viewModel.size.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tamagotchi Replica",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Size: $size",
            style = MaterialTheme.typography.titleLarge
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Display the square grid
        TamagochiGrid(
            size = size,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = { viewModel.decreaseSize() }) {
            Text("Decrease Size")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Long press the glyph to increase size",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

