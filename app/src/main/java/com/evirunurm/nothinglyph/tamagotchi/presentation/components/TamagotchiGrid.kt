package com.evirunurm.nothinglyph.tamagotchi.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import com.evirunurm.nothinglyph.tamagotchi.domain.GlyphGrid
import com.evirunurm.nothinglyph.tamagotchi.domain.TamagotchiShape

@Composable
fun TamagotchiGrid(
    size: Int,
    modifier: Modifier = Modifier
) {
    val shape = remember(size) { TamagotchiShape(size) }

    Canvas(
        modifier = modifier
            // Use graphicsLayer to cache the canvas drawing
            .graphicsLayer()
    ) {
        val cellSize = this.size.width / GlyphGrid.DEFAULT_GRID_SIZE

        drawBorder(cellSize, Color(0xFF000000))
        drawBackground(cellSize, Color(0xFF1C1C1C))
        drawTamagotchiShape(shape, cellSize)
        drawGridLines(cellSize, Color(0xFF000000), 10f)
    }
}

private fun DrawScope.drawBackground(cellSize: Float, color: Color) {
    for ((x, y) in GlyphGrid.GRID_PATTERN) {
        drawRect(
            color = color,
            topLeft = Offset(x * cellSize, y * cellSize),
            size = Size(cellSize, cellSize)
        )
    }
}

private fun DrawScope.drawTamagotchiShape(shape: TamagotchiShape, cellSize: Float) {
    for (x in shape.startX..shape.endX) {
        for (y in shape.startY..shape.endY) {
            // Draw if the cell is within bounds and the circular pattern
            if (x in 0..<GlyphGrid.DEFAULT_GRID_SIZE &&
                y >= 0 &&
                y < GlyphGrid.DEFAULT_GRID_SIZE &&
                (x to y) in GlyphGrid.GRID_PATTERN) {
                drawRect(
                    color = Color.White,
                    topLeft = Offset(x * cellSize, y * cellSize),
                    size = Size(cellSize, cellSize)
                )
            }
        }
    }
}

private fun DrawScope.drawGridLines(cellSize: Float, color: Color, strokeWidth: Float) {
    // Track which lines we've already drawn to avoid duplicates
    val drawnVerticalLines = mutableSetOf<Pair<Int, Int>>()
    val drawnHorizontalLines = mutableSetOf<Pair<Int, Int>>()

    for ((row, column) in GlyphGrid.GRID_PATTERN) {
        // Top horizontal line for a cell
        if ((row to column) !in drawnHorizontalLines) {
            drawLine(
                color = color,
                start = Offset((row * cellSize) - strokeWidth / 2, column * cellSize),
                end = Offset(((row + 1) * cellSize) + strokeWidth / 2, column * cellSize),
                strokeWidth = strokeWidth
            )
            drawnHorizontalLines.add(row to column)
        }

        // Left vertical line for a cell
        if ((row to column) !in drawnVerticalLines) {
            drawLine(
                color = color,
                start = Offset((row * cellSize), (column * cellSize) - strokeWidth / 2),
                end = Offset(row * cellSize, ((column + 1) * cellSize) + strokeWidth / 2),
                strokeWidth = strokeWidth
            )
            drawnVerticalLines.add(row to column)
        }

        // Right vertical line, if there's no cell to the right
        if ((row + 1 to column) !in GlyphGrid.GRID_PATTERN) {
            drawLine(
                color = color,
                start = Offset((row + 1) * cellSize, (column * cellSize) - strokeWidth / 2),
                end = Offset((row + 1) * cellSize, ((column + 1) * cellSize) + strokeWidth / 2),
                strokeWidth = strokeWidth
            )
        }

        // Bottom horizontal line, if there's no cell below
        if ((row to column + 1) !in GlyphGrid.GRID_PATTERN) {
            drawLine(
                color = color,
                start = Offset((row * cellSize) - strokeWidth / 2, (column + 1) * cellSize),
                end = Offset(((row + 1) * cellSize) + strokeWidth / 2, (column + 1) * cellSize),
                strokeWidth = strokeWidth
            )
        }
    }
}

private fun DrawScope.drawBorder(cellSize: Float, color: Color) {
    val halfGridSize = GlyphGrid.DEFAULT_GRID_SIZE.toFloat() / 2
    val radius = halfGridSize * cellSize

    drawCircle(
        color = color,
        radius = radius,
        center = Offset(radius, radius),
        style = Stroke(width = halfGridSize * 10)
    )
}
