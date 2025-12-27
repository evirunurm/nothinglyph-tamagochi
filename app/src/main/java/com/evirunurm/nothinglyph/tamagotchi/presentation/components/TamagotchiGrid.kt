package com.evirunurm.nothinglyph.tamagotchi.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.evirunurm.nothinglyph.tamagotchi.domain.TamagotchiShape

private val SHAPE = setOf(
    0 to 9, 0 to 10, 0 to 11, 0 to 12, 0 to 13, 0 to 14, 0 to 15,
    1 to 7, 1 to 8, 1 to 9, 1 to 10, 1 to 11, 1 to 12, 1 to 13, 1 to 14, 1 to 15, 1 to 16, 1 to 17,
    2 to 5, 2 to 6, 2 to 7, 2 to 8, 2 to 9, 2 to 10, 2 to 11, 2 to 12, 2 to 13, 2 to 14, 2 to 15, 2 to 16, 2 to 17, 2 to 18, 2 to 19,
    3 to 4, 3 to 5, 3 to 6, 3 to 7, 3 to 8, 3 to 9, 3 to 10, 3 to 11, 3 to 12, 3 to 13, 3 to 14, 3 to 15, 3 to 16, 3 to 17, 3 to 18, 3 to 19, 3 to 20,
    4 to 3, 4 to 4, 4 to 5, 4 to 6, 4 to 7, 4 to 8, 4 to 9, 4 to 10, 4 to 11, 4 to 12, 4 to 13, 4 to 14, 4 to 15, 4 to 16, 4 to 17, 4 to 18, 4 to 19, 4 to 20, 4 to 21,
    5 to 2, 5 to 3, 5 to 4, 5 to 5, 5 to 6, 5 to 7, 5 to 8, 5 to 9, 5 to 10, 5 to 11, 5 to 12, 5 to 13, 5 to 14, 5 to 15, 5 to 16, 5 to 17, 5 to 18, 5 to 19, 5 to 20, 5 to 21, 5 to 22,
    6 to 2, 6 to 3, 6 to 4, 6 to 5, 6 to 6, 6 to 7, 6 to 8, 6 to 9, 6 to 10, 6 to 11, 6 to 12, 6 to 13, 6 to 14, 6 to 15, 6 to 16, 6 to 17, 6 to 18, 6 to 19, 6 to 20, 6 to 21, 6 to 22,
    7 to 1, 7 to 2, 7 to 3, 7 to 4, 7 to 5, 7 to 6, 7 to 7, 7 to 8, 7 to 9, 7 to 10, 7 to 11, 7 to 12, 7 to 13, 7 to 14, 7 to 15, 7 to 16, 7 to 17, 7 to 18, 7 to 19, 7 to 20, 7 to 21, 7 to 22, 7 to 23,
    8 to 1, 8 to 2, 8 to 3, 8 to 4, 8 to 5, 8 to 6, 8 to 7, 8 to 8, 8 to 9, 8 to 10, 8 to 11, 8 to 12, 8 to 13, 8 to 14, 8 to 15, 8 to 16, 8 to 17, 8 to 18, 8 to 19, 8 to 20, 8 to 21, 8 to 22, 8 to 23,
    9 to 0, 9 to 1, 9 to 2, 9 to 3, 9 to 4, 9 to 5, 9 to 6, 9 to 7, 9 to 8, 9 to 9, 9 to 10, 9 to 11, 9 to 12, 9 to 13, 9 to 14, 9 to 15, 9 to 16, 9 to 17, 9 to 18, 9 to 19, 9 to 20, 9 to 21, 9 to 22, 9 to 23, 9 to 24,
    10 to 0, 10 to 1, 10 to 2, 10 to 3, 10 to 4, 10 to 5, 10 to 6, 10 to 7, 10 to 8, 10 to 9, 10 to 10, 10 to 11, 10 to 12, 10 to 13, 10 to 14, 10 to 15, 10 to 16, 10 to 17, 10 to 18, 10 to 19, 10 to 20, 10 to 21, 10 to 22, 10 to 23, 10 to 24,
    11 to 0, 11 to 1, 11 to 2, 11 to 3, 11 to 4, 11 to 5, 11 to 6, 11 to 7, 11 to 8, 11 to 9, 11 to 10, 11 to 11, 11 to 12, 11 to 13, 11 to 14, 11 to 15, 11 to 16, 11 to 17, 11 to 18, 11 to 19, 11 to 20, 11 to 21, 11 to 22, 11 to 23, 11 to 24,
    12 to 0, 12 to 1, 12 to 2, 12 to 3, 12 to 4, 12 to 5, 12 to 6, 12 to 7, 12 to 8, 12 to 9, 12 to 10, 12 to 11, 12 to 12, 12 to 13, 12 to 14, 12 to 15, 12 to 16, 12 to 17, 12 to 18, 12 to 19, 12 to 20, 12 to 21, 12 to 22, 12 to 23, 12 to 24,
    13 to 0, 13 to 1, 13 to 2, 13 to 3, 13 to 4, 13 to 5, 13 to 6, 13 to 7, 13 to 8, 13 to 9, 13 to 10, 13 to 11, 13 to 12, 13 to 13, 13 to 14, 13 to 15, 13 to 16, 13 to 17, 13 to 18, 13 to 19, 13 to 20, 13 to 21, 13 to 22, 13 to 23, 13 to 24,
    14 to 0, 14 to 1, 14 to 2, 14 to 3, 14 to 4, 14 to 5, 14 to 6, 14 to 7, 14 to 8, 14 to 9, 14 to 10, 14 to 11, 14 to 12, 14 to 13, 14 to 14, 14 to 15, 14 to 16, 14 to 17, 14 to 18, 14 to 19, 14 to 20, 14 to 21, 14 to 22, 14 to 23, 14 to 24,
    15 to 0, 15 to 1, 15 to 2, 15 to 3, 15 to 4, 15 to 5, 15 to 6, 15 to 7, 15 to 8, 15 to 9, 15 to 10, 15 to 11, 15 to 12, 15 to 13, 15 to 14, 15 to 15, 15 to 16, 15 to 17, 15 to 18, 15 to 19, 15 to 20, 15 to 21, 15 to 22, 15 to 23, 15 to 24,
    16 to 1, 16 to 2, 16 to 3, 16 to 4, 16 to 5, 16 to 6, 16 to 7, 16 to 8, 16 to 9, 16 to 10, 16 to 11, 16 to 12, 16 to 13, 16 to 14, 16 to 15, 16 to 16, 16 to 17, 16 to 18, 16 to 19, 16 to 20, 16 to 21, 16 to 22, 16 to 23,
    17 to 1, 17 to 2, 17 to 3, 17 to 4, 17 to 5, 17 to 6, 17 to 7, 17 to 8, 17 to 9, 17 to 10, 17 to 11, 17 to 12, 17 to 13, 17 to 14, 17 to 15, 17 to 16, 17 to 17, 17 to 18, 17 to 19, 17 to 20, 17 to 21, 17 to 22, 17 to 23,
    18 to 2, 18 to 3, 18 to 4, 18 to 5, 18 to 6, 18 to 7, 18 to 8, 18 to 9, 18 to 10, 18 to 11, 18 to 12, 18 to 13, 18 to 14, 18 to 15, 18 to 16, 18 to 17, 18 to 18, 18 to 19, 18 to 20, 18 to 21, 18 to 22,
    19 to 2, 19 to 3, 19 to 4, 19 to 5, 19 to 6, 19 to 7, 19 to 8, 19 to 9, 19 to 10, 19 to 11, 19 to 12, 19 to 13, 19 to 14, 19 to 15, 19 to 16, 19 to 17, 19 to 18, 19 to 19, 19 to 20, 19 to 21, 19 to 22,
    20 to 3, 20 to 4, 20 to 5, 20 to 6, 20 to 7, 20 to 8, 20 to 9, 20 to 10, 20 to 11, 20 to 12, 20 to 13, 20 to 14, 20 to 15, 20 to 16, 20 to 17, 20 to 18, 20 to 19, 20 to 20, 20 to 21,
    21 to 4, 21 to 5, 21 to 6, 21 to 7, 21 to 8, 21 to 9, 21 to 10, 21 to 11, 21 to 12, 21 to 13, 21 to 14, 21 to 15, 21 to 16, 21 to 17, 21 to 18, 21 to 19, 21 to 20,
    22 to 5, 22 to 6, 22 to 7, 22 to 8, 22 to 9, 22 to 10, 22 to 11, 22 to 12, 22 to 13, 22 to 14, 22 to 15, 22 to 16, 22 to 17, 22 to 18, 22 to 19,
    23 to 7, 23 to 8, 23 to 9, 23 to 10, 23 to 11, 23 to 12, 23 to 13, 23 to 14, 23 to 15, 23 to 16, 23 to 17,
    24 to 9, 24 to 10, 24 to 11, 24 to 12, 24 to 13, 24 to 14, 24 to 15
)

@Composable
fun TamagotchiGrid(
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

        // Draw the circular border pattern (background)
        for ((x, y) in SHAPE) {
            drawRect(
                color = Color.Gray.copy(alpha = 0.3f),
                topLeft = Offset(x * cellSize, y * cellSize),
                size = Size(cellSize, cellSize)
            )
        }

        val shape = TamagotchiShape(size)

        // Draw the filled square (shape)
        for (x in shape.startX..shape.endX) {
            for (y in shape.startY..shape.endY) {
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
