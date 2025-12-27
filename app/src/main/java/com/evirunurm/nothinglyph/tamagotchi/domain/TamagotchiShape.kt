package com.evirunurm.nothinglyph.tamagotchi.domain

/**
 * Represents the visual shape of a Tamagotchi on the glyph matrix.
 * Encapsulates the logic for calculating the position and rendering the square shape.
 *
 * @property size The size of the Tamagotchi
 * @property gridWidth The width of the glyph grid (default: [GlyphGrid.DEFAULT_GRID_SIZE])
 * @property gridHeight The height of the glyph grid (default: [GlyphGrid.DEFAULT_GRID_SIZE])
 */
data class TamagotchiShape(
    val size: Int,
    val gridWidth: Int = GlyphGrid.DEFAULT_GRID_SIZE,
    val gridHeight: Int = GlyphGrid.DEFAULT_GRID_SIZE
) {
    val visualSize: Int = 2 * size - 1

    private val center: Int = gridWidth / 2

    private val half: Int = visualSize / 2

    val startX: Int = center - half

    val endX: Int = center + half

    val startY: Int = center - half

    val endY: Int = center + half

    fun toGlyphArray(brightness: Int = GlyphGrid.MAX_BRIGHTNESS): IntArray {
        val array = IntArray(gridWidth * gridHeight)

        for (x in startX..endX) {
            for (y in startY..endY) {
                // Only set brightness if the cell is within bounds AND within the circular pattern
                if (x in 0..<gridWidth &&
                    y >= 0 &&
                    y < gridHeight &&
                    (x to y) in GlyphGrid.GRID_PATTERN) {
                    array[y * gridWidth + x] = brightness
                }
            }
        }

        return array
    }

    fun contains(x: Int, y: Int): Boolean {
        return x in startX..endX && y in startY..endY
    }
}
