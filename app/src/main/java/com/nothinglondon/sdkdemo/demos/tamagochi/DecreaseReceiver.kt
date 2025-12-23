package com.nothinglondon.sdkdemo.demos.tamagochi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DecreaseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("DecreaseReceiver", "Received decrease broadcast")
        context?.let {
            val repository = TamagochiRepository.getInstance(it)
            repository.decreaseSize()
        }
    }
}
