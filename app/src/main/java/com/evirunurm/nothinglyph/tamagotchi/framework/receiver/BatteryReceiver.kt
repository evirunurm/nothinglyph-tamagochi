package com.evirunurm.nothinglyph.tamagotchi.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

/**
 * Broadcast receiver to update the battery level.
 *
 * _See more about [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver)_
 * @param onBatteryLevelChanged Callback to be invoked when the battery level changes.
 */
class BatteryReceiver(private val onBatteryLevelChanged: (Int) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPercentage = if (level != -1 && scale != -1) {
                // Calculate battery percentage based on level and scale (maximum battery level)
                (level / scale.toFloat() * 100).toInt()
            } else {
                // Default to 100% if we can't get the level
                100
            }
            onBatteryLevelChanged(batteryPercentage)
        }
    }
}