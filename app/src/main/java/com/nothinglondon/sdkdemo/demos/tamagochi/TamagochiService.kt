package com.nothinglondon.sdkdemo.demos.tamagochi

import android.content.Context
import android.content.SharedPreferences
import com.nothing.ketchum.GlyphMatrixManager
import com.nothinglondon.sdkdemo.demos.GlyphMatrixService
import androidx.core.content.edit

class TamagochiService : GlyphMatrixService("Tamagochi") {

    private lateinit var prefs: SharedPreferences
    private var size = 1

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        prefs = context.getSharedPreferences("tamagochi_prefs", MODE_PRIVATE)
        size = prefs.getInt("size", 1)
        displaySquare(size, glyphMatrixManager)
    }

    override fun onTouchPointLongPress() {
        size++
        if (size > 25) size = 1
        prefs.edit { putInt("size", size) }
        glyphMatrixManager?.let { displaySquare(size, it) }
    }

    private fun displaySquare(size: Int, glyphMatrixManager: GlyphMatrixManager) {
        val array = IntArray(GLYPH_ARRAY_SIZE) { 0 }
        val center = GLYPH_WIDTH / 2
        val half = size / 2
        val startX = center - half
        val endX = center + half
        val startY = center - half
        val endY = center + half
        for (x in startX..endX) {
            for (y in startY..endY) {
                if (x >= 0 && x < GLYPH_WIDTH && y >= 0 && y < GLYPH_HEIGHT) {
                    array[y * GLYPH_WIDTH + x] = MAX_BRIGHTNESS
                }
            }
        }
        glyphMatrixManager.setMatrixFrame(array)
    }

    companion object {
        private const val GLYPH_WIDTH = 25
        private const val GLYPH_HEIGHT = 25
        private const val GLYPH_ARRAY_SIZE = GLYPH_WIDTH * GLYPH_HEIGHT
        private const val MAX_BRIGHTNESS = 2047
    }
}
