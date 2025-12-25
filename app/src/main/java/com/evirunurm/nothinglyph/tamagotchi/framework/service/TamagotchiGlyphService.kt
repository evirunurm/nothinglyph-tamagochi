package com.evirunurm.nothinglyph.tamagotchi.framework.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.evirunurm.nothinglyph.service.GlyphMatrixService
import com.nothing.ketchum.GlyphMatrixManager
import com.evirunurm.nothinglyph.tamagotchi.data.repositories.TamagotchiRepository

class TamagotchiGlyphService : GlyphMatrixService("Tamagotchi") {

    private lateinit var repository: TamagotchiRepository
    private var sizeChangeReceiver: BroadcastReceiver? = null

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        Log.d("TamagotchiService", "Service connected")
        repository = TamagotchiRepository.getInstance(context)
        val size = repository.getSize()
        displaySquare(size, glyphMatrixManager)

        repository.startDecreasing()

        sizeChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val newSize = intent?.getIntExtra(TamagotchiRepository.EXTRA_SIZE, repository.getSize()) ?: repository.getSize()
                Log.d("TamagotchiService", "Received size change broadcast, updating glyph to size $newSize")
                glyphMatrixManager.let { displaySquare(newSize, it) }
            }
        }
        val filter = IntentFilter(TamagotchiRepository.ACTION_SIZE_CHANGED)
        context.registerReceiver(sizeChangeReceiver, filter, RECEIVER_EXPORTED)
    }

    override fun performOnServiceDisconnected(context: Context) {
        Log.d("TamagotchiService", "Service disconnected")
        sizeChangeReceiver?.let { context.unregisterReceiver(it) }
        sizeChangeReceiver = null
    }

    override fun onTouchPointLongPress() {
        Log.d("TamagotchiService", "Long press detected, increasing size")
        repository.increaseSize()
    }

    private fun displaySquare(size: Int, glyphMatrixManager: GlyphMatrixManager) {
        val visualSize = 2 * size - 1
        val array = IntArray(GLYPH_ARRAY_SIZE) { 0 }
        val center = GLYPH_WIDTH / 2
        val half = visualSize / 2
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
