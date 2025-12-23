package com.nothinglondon.sdkdemo.demos.tamagochi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.nothing.ketchum.GlyphMatrixManager
import com.nothinglondon.sdkdemo.demos.GlyphMatrixService

class TamagochiService : GlyphMatrixService("Tamagochi") {

    private lateinit var repository: TamagochiRepository
    private var sizeChangeReceiver: BroadcastReceiver? = null

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        Log.d("TamagochiService", "Service connected")
        repository = TamagochiRepository.getInstance(context)
        val size = repository.getSize()
        displaySquare(size, glyphMatrixManager)

        repository.startDecreasing()

        sizeChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val newSize = intent?.getIntExtra(TamagochiRepository.EXTRA_SIZE, repository.getSize()) ?: repository.getSize()
                Log.d("TamagochiService", "Received size change broadcast, updating glyph to size $newSize")
                glyphMatrixManager.let { displaySquare(newSize, it) }
            }
        }
        val filter = IntentFilter(TamagochiRepository.ACTION_SIZE_CHANGED)
        context.registerReceiver(sizeChangeReceiver, filter, RECEIVER_EXPORTED)
    }

    override fun performOnServiceDisconnected(context: Context) {
        Log.d("TamagochiService", "Service disconnected")
        sizeChangeReceiver?.let { context.unregisterReceiver(it) }
        sizeChangeReceiver = null
    }

    override fun onTouchPointLongPress() {
        Log.d("TamagochiService", "Long press detected, increasing size")
        repository.increaseSize()
        val newSize = repository.getSize()
        glyphMatrixManager?.let { displaySquare(newSize, it) }
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
