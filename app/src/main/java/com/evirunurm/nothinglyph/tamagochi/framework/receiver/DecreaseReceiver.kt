package com.evirunurm.nothinglyph.tamagochi.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.evirunurm.nothinglyph.tamagochi.data.repositories.TamagochiRepository

/**
 * Broadcast receiver to decrease the size of the square.
 * Allows communication between the service and the view model.
 *
 * _See more about [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver)_
 */
class DecreaseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val repository = TamagochiRepository.getInstance(it)
            repository.decreaseSize()
        }
    }
}