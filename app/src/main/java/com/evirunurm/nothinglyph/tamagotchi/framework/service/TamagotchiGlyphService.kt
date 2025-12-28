package com.evirunurm.nothinglyph.tamagotchi.framework.service

import android.content.Context
import com.evirunurm.nothinglyph.service.GlyphMatrixService
import com.nothing.ketchum.GlyphMatrixManager
import com.evirunurm.nothinglyph.tamagotchi.data.repositories.TamagotchiRepository
import com.evirunurm.nothinglyph.tamagotchi.domain.TamagotchiShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TamagotchiGlyphService : GlyphMatrixService("Tamagotchi") {

    private lateinit var repository: TamagotchiRepository
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        repository = TamagotchiRepository.getInstance(context)
        val size = repository.getSize()
        val energy = repository.getCurrentEnergy()
        displaySquare(size, energy, glyphMatrixManager)

        repository.startDecreasing()

        scope.launch {
            launch {
                repository.sizeFlow.collect { newSize ->
                    val currentEnergy = repository.energyFlow.value
                    displaySquare(newSize, currentEnergy, glyphMatrixManager)
                }
            }
            launch {
                repository.energyFlow.collect { newEnergy ->
                    val currentSize = repository.sizeFlow.value
                    displaySquare(currentSize, newEnergy, glyphMatrixManager)
                }
            }
        }
    }

    override fun performOnServiceDisconnected(context: Context) {
        scope.cancel()
    }

    override fun onTouchPointLongPress() {
        repository.increaseSize()
    }

    private fun displaySquare(size: Int, energy: Int, glyphMatrixManager: GlyphMatrixManager) {
        val shape = TamagotchiShape(size, energy = energy)
        val array = shape.toGlyphArray()
        glyphMatrixManager.setMatrixFrame(array)
    }
}
