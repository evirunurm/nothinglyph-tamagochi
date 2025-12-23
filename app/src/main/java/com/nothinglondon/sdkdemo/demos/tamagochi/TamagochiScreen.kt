package com.nothinglondon.sdkdemo.demos.tamagochi

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

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

@Composable
fun TamagochiGrid(
    size: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val gridSize = 25
        val cellSize = this.size.width / gridSize
        
        // Draw grid background
        drawRect(
            color = Color.Black,
            topLeft = Offset.Zero,
            size = this.size
        )
        
        // Calculate the square position (centered)
        val center = gridSize / 2
        val half = size / 2
        val startX = center - half
        val endX = center + half
        val startY = center - half
        val endY = center + half
        
        // Draw the filled square
        for (x in startX..endX) {
            for (y in startY..endY) {
                if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(x * cellSize, y * cellSize),
                        size = Size(cellSize, cellSize)
                    )
                }
            }
        }
        
        // Draw grid lines
        for (i in 0..gridSize) {
            val pos = i * cellSize
            // Vertical lines
            drawLine(
                color = Color.Gray.copy(alpha = 0.3f),
                start = Offset(pos, 0f),
                end = Offset(pos, this.size.height),
                strokeWidth = 1f
            )
            // Horizontal lines
            drawLine(
                color = Color.Gray.copy(alpha = 0.3f),
                start = Offset(0f, pos),
                end = Offset(this.size.width, pos),
                strokeWidth = 1f
            )
        }
    }
}

