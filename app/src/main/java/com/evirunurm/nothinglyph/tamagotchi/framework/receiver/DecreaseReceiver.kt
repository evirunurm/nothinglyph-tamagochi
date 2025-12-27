package com.evirunurm.nothinglyph.tamagotchi.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.evirunurm.nothinglyph.tamagotchi.data.repositories.TamagotchiRepository

/**
 * Broadcast receiver to decrease the size of the square.
 * Allows communication between the service and the view model.
 *
 * _See more about [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver)_
 */
class DecreaseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val repository = TamagotchiRepository.getInstance(it)
            repository.decreaseSize()
        }
    }
}