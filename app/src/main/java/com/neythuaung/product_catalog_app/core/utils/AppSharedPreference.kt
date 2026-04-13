package com.neythuaung.product_catalog_app.core.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import javax.inject.Inject

class AppSharedPreference @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val FILE_NAME = "DATA"
        private const val KEY_POS_SYNC_TIME = "posSyncTime"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    // ------------------ Save Methods ------------------

    fun saveString(key: String, value: String?) = sharedPref.edit { putString(key, value) }

    fun saveInt(key: String, value: Int) = sharedPref.edit { putInt(key, value) }

    fun saveBoolean(key: String, value: Boolean) = sharedPref.edit { putBoolean(key, value) }

    fun saveFloat(key: String, value: Float) = sharedPref.edit { putFloat(key, value) }

    fun saveLong(key: String, value: Long) = sharedPref.edit { putLong(key, value) }

    fun saveLastFetchPosDate(value: String) =
        sharedPref.edit { putString(KEY_POS_SYNC_TIME, value) }

    // ------------------ Get Methods ------------------

    fun getString(key: String, default: String? = null): String? =
        sharedPref.getString(key, default)

    fun getInt(key: String, default: Int = 0): Int =
        sharedPref.getInt(key, default)

    fun getBoolean(key: String, default: Boolean = false): Boolean =
        sharedPref.getBoolean(key, default)

    fun getFloat(key: String, default: Float = 0f): Float =
        sharedPref.getFloat(key, default)

    fun getLong(key: String, default: Long = 0L): Long =
        sharedPref.getLong(key, default)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLastSavedAt(): String =
        sharedPref.getString(KEY_POS_SYNC_TIME, LocalDateTime.now().toString()).toString()

    // ------------------ Utility Methods ------------------

    fun contains(key: String): Boolean = sharedPref.contains(key)

    fun clear() = sharedPref.edit { clear() }

    fun remove(key: String) = sharedPref.edit { remove(key) }

    // ------------------ Generic Get ------------------
    inline fun <reified T> get(key: String, default: T): T = when (T::class) {
        String::class -> getString(key, default as? String) as T
        Int::class -> getInt(key, default as Int) as T
        Boolean::class -> getBoolean(key, default as Boolean) as T
        Float::class -> getFloat(key, default as Float) as T
        Long::class -> getLong(key, default as Long) as T
        else -> throw IllegalArgumentException("Unsupported type")
    }
}