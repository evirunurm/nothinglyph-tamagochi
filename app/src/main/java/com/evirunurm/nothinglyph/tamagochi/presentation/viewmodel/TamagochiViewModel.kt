package com.evirunurm.nothinglyph.tamagochi.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.evirunurm.nothinglyph.tamagochi.data.repositories.TamagochiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the Tamagochi demo.
 * @param application The application context.
 */
class TamagochiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TamagochiRepository.getInstance(application)

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

