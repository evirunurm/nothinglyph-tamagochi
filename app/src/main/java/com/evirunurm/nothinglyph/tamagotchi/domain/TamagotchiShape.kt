package com.evirunurm.nothinglyph.tamagotchi.domain

/**
 * Represents the visual shape of a Tamagotchi on the glyph matrix.
 * Encapsulates the logic for calculating the position and rendering the square shape.
 *
 * @property size The size of the Tamagotchi (1-13)
 * @property gridWidth The width of the glyph grid (default: 25)
 * @property gridHeight The height of the glyph grid (default: 25)
 */
data class TamagotchiShape(
    val size: Int,
    val gridWidth: Int = DEFAULT_GRID_WIDTH,
    val gridHeight: Int = DEFAULT_GRID_HEIGHT
) {
    val visualSize: Int = 2 * size - 1

    private val center: Int = gridWidth / 2

    private val half: Int = visualSize / 2

    val startX: Int = center - half

    val endX: Int = center + half

    val startY: Int = center - half

    val endY: Int = center + half

    fun toGlyphArray(brightness: Int = MAX_BRIGHTNESS): IntArray {
        val array = IntArray(gridWidth * gridHeight)
        
        for (x in startX..endX) {
            for (y in startY..endY) {
                if (x in 0..<gridWidth && y >= 0 && y < gridHeight) {
                    array[y * gridWidth + x] = brightness
                }
            }
        }
        
        return array
    }

    fun contains(x: Int, y: Int): Boolean {
        return x in startX..endX && y in startY..endY
    }

    companion object {
        const val DEFAULT_GRID_WIDTH = 25
        const val DEFAULT_GRID_HEIGHT = 25
        const val MAX_BRIGHTNESS = 2047
    }
}

