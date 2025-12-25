package com.evirunurm.nothinglyph.tamagotchi.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.evirunurm.nothinglyph.tamagotchi.data.repositories.TamagotchiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the Tamagotchi demo.
 * @param application The application context.
 */
class TamagotchiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TamagotchiRepository.getInstance(application)

    val size: StateFlow<Int> = repository.sizeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = repository.getSize()
        )

    fun decreaseSize() {
        repository.decreaseSize()
    }
}

