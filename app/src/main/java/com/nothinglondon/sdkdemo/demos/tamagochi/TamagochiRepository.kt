package com.nothinglondon.sdkdemo.demos.tamagochi

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.SystemClock
import android.util.Log
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TamagochiRepository private constructor(context: Context) {

    private val prefs: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val appContext = context.applicationContext

    private val _sizeFlow = MutableSharedFlow<Int>(replay = 1)
    val sizeFlow: Flow<Int> = _sizeFlow

    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null

    init {
        // Emit initial value
        _sizeFlow.tryEmit(getSize())

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_SIZE) {
                _sizeFlow.tryEmit(getSize())
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun getSize(): Int {
        return prefs.getInt(KEY_SIZE, DEFAULT_SIZE)
    }

    fun setSize(size: Int) {
        val newSize = size.coerceIn(MIN_SIZE, MAX_SIZE)
        prefs.edit { putInt(KEY_SIZE, newSize) }
        broadcastSizeChange()
    }

    fun increaseSize() {
        val currentSize = getSize()
        val newSize = if (currentSize >= MAX_SIZE) MIN_SIZE else currentSize + 1
        setSize(newSize)
    }

    fun decreaseSize() {
        Log.d("TamagochiRepository", "Decreasing size")
        val currentSize = getSize()
        if (currentSize > MIN_SIZE) {
            setSize(currentSize - 1)
        }
    }

    private fun broadcastSizeChange() {
        val size = getSize()
        Log.d("TamagochiRepository", "Broadcasting size change: $size")
        val intent = Intent(ACTION_SIZE_CHANGED).apply {
            putExtra(EXTRA_SIZE, size)
            setPackage(appContext.packageName)
        }
        appContext.sendBroadcast(intent)
    }

    fun startDecreasing() {
        Log.d("TamagochiRepository", "Starting alarm for decreasing")
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
        Log.d("TamagochiRepository", "Stopping alarm")
        pendingIntent?.let { pi ->
            alarmManager?.cancel(pi)
        }
        alarmManager = null
        pendingIntent = null
    }

    companion object {
        private const val PREFS_NAME = "tamagochi_prefs"
        private const val KEY_SIZE = "size"
        private const val DEFAULT_SIZE = 1
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 25
        
        const val ACTION_SIZE_CHANGED = "com.nothinglondon.sdkdemo.TAMAGOCHI_SIZE_CHANGED"
        const val EXTRA_SIZE = "size"

        @Volatile
        private var instance: TamagochiRepository? = null

        fun getInstance(context: Context): TamagochiRepository {
            return instance ?: synchronized(this) {
                instance ?: TamagochiRepository(context).also { instance = it }
            }
        }
    }
}
