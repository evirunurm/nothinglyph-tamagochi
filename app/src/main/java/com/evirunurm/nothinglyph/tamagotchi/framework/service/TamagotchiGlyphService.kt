package com.evirunurm.nothinglyph.tamagotchi.framework.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.evirunurm.nothinglyph.service.GlyphMatrixService
import com.nothing.ketchum.GlyphMatrixManager
import com.evirunurm.nothinglyph.tamagotchi.data.repositories.TamagotchiRepository
import com.evirunurm.nothinglyph.tamagotchi.domain.TamagotchiShape

class TamagotchiGlyphService : GlyphMatrixService("Tamagotchi") {

    private lateinit var repository: TamagotchiRepository
    private var sizeChangeReceiver: BroadcastReceiver? = null

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        repository = TamagotchiRepository.getInstance(context)
        val size = repository.getSize()
        displaySquare(size, glyphMatrixManager)

        repository.startDecreasing()

        sizeChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val newSize = intent?.getIntExtra(TamagotchiRepository.EXTRA_SIZE, repository.getSize()) ?: repository.getSize()
                glyphMatrixManager.let { displaySquare(newSize, it) }
            }
        }
        val filter = IntentFilter(TamagotchiRepository.ACTION_SIZE_CHANGED)
        context.registerReceiver(sizeChangeReceiver, filter, RECEIVER_EXPORTED)
    }

    override fun performOnServiceDisconnected(context: Context) {
        sizeChangeReceiver?.let { context.unregisterReceiver(it) }
        sizeChangeReceiver = null
    }

    override fun onTouchPointLongPress() {
        repository.increaseSize()
    }

    private fun displaySquare(size: Int, glyphMatrixManager: GlyphMatrixManager) {
        val shape = TamagotchiShape(size)
        val array = shape.toGlyphArray()
        glyphMatrixManager.setMatrixFrame(array)
    }
}
