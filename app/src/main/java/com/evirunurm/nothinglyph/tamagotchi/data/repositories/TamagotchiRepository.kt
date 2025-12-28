package com.evirunurm.nothinglyph.tamagotchi.data.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.BatteryManager
import android.os.SystemClock
import android.util.Log
import androidx.core.content.edit
import com.evirunurm.nothinglyph.tamagotchi.framework.receiver.BatteryReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TamagotchiRepository private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val appContext = context.applicationContext

    private val _sizeFlow = MutableStateFlow(getSize())
    val sizeFlow: StateFlow<Int> = _sizeFlow

    private val _energyFlow = MutableStateFlow(getCurrentEnergy())
    val energyFlow: StateFlow<Int> = _energyFlow

    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null

    private val batteryReceiver = BatteryReceiver { percentage ->
        _energyFlow.value = percentage
    }

    init {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        appContext.registerReceiver(batteryReceiver, filter)
    }

    fun getSize(): Int {
        return prefs.getInt(KEY_SIZE, DEFAULT_SIZE)
    }

    fun getCurrentEnergy(): Int {
        val batteryManager = appContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    fun setSize(size: Int) {
        val newSize = size.coerceIn(MIN_SIZE, MAX_SIZE)
        _sizeFlow.value = newSize
        prefs.edit { putInt(KEY_SIZE, newSize) }
    }

    fun increaseSize() {
        val currentSize = getSize()
        val newSize = if (currentSize >= MAX_SIZE) MAX_SIZE else currentSize + 1
        setSize(newSize)
    }

    fun decreaseSize() {
        Log.d("TamagotchiRepository", "Decreasing size")
        val currentSize = getSize()
        if (currentSize > MIN_SIZE) {
            setSize(currentSize - 1)
        }
    }

    fun startDecreasing() {
        Log.d("TamagotchiRepository", "Starting alarm for decreasing")
        if (alarmManager == null) {
            alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent("DECREASE_SIZE")
            pendingIntent = PendingIntent.getBroadcast(
                appContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager?.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 5000,
                5000,
                pendingIntent!!
            )
        }
    }

    fun stopDecreasing() {
        Log.d("TamagotchiRepository", "Stopping alarm")
        pendingIntent?.let { pi ->
            alarmManager?.cancel(pi)
        }
        alarmManager = null
        pendingIntent = null
    }

    companion object {
        private const val PREFS_NAME = "tamagotchi_prefs"
        private const val KEY_SIZE = "size"
        private const val DEFAULT_SIZE = 1
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 13

        // TODO: What is this??
        @Volatile
        private var instance: TamagotchiRepository? = null

        fun getInstance(context: Context): TamagotchiRepository {
            return instance ?: synchronized(this) {
                instance ?: TamagotchiRepository(context).also { instance = it }
            }
        }
    }
}
