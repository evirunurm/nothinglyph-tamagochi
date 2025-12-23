package com.evirunurm.nothinglyph.tamagochi.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

data class SquarePosition(
    val startX: Int,
    val endX: Int,
    val startY: Int,
    val endY: Int
)

@Composable
fun TamagochiGrid(
    size: Int,
    modifier: Modifier = Modifier
) {
    fun calculateSquarePosition(gridSize: Int, size: Int):SquarePosition {
        val visualSize = 2 * size - 1
        val center = gridSize / 2
        val half = visualSize / 2
        val startX = center - half
        val endX = center + half
        val startY = center - half
        val endY = center + half
        return SquarePosition(startX, endX, startY, endY)
    }

    Canvas(modifier = modifier) {
        val gridSize = 25
        val cellSize = this.size.width / gridSize

        // Draw grid background
        drawRect(
            color = Color.Black,
            topLeft = Offset.Zero,
            size = this.size
        )

        val (startX, endX, startY, endY) = calculateSquarePosition(gridSize, size)

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
