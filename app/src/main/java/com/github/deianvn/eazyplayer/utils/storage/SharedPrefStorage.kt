package com.github.deianvn.eazyplayer.utils.storage

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.squareup.moshi.Moshi


class SharedPrefStorage(
    private val context: Context,
    private val moshi: Moshi
) {

    companion object {
        private const val APP_STORAGE = "com.github.deianvn.eazyplayer"
    }

    @WorkerThread
    fun putString(key: String, value: String) {
        getSharedPreferences().edit(commit = true) {
            putString(key, value)
        }
    }

    @WorkerThread
    fun getString(key: String): String? {
        return getSharedPreferences()
            .getString(key, null)
    }

    @WorkerThread
    fun <T> putObject(key: String, value: T, javaType: Class<T>) {
        val json = moshi.adapter(javaType).toJson(value)
        putString(key, json)
    }

    @WorkerThread
    fun <T> getObject(key: String, javaType: Class<T>): T? {
        val json = getString(key) ?: return null
        return moshi.adapter(javaType).fromJson(json)
    }

    @WorkerThread
    fun clear(key: String) {
        getSharedPreferences()
            .edit(commit = true) {
                remove(key)
            }
    }

    @WorkerThread
    fun contains(key: String): Boolean {
        return getSharedPreferences().contains(key)
    }

    private fun getSharedPreferences() = context.getSharedPreferences(
        APP_STORAGE, Context.MODE_PRIVATE
    )

}
