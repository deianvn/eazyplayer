package com.github.deianvn.eazyplayer.state.repository

import com.github.deianvn.compose.director.utils.toStageError
import com.github.deianvn.eazyplayer.state.dto.ConfigDTO
import com.github.deianvn.eazyplayer.state.model.Config
import com.github.deianvn.eazyplayer.utils.coroutines.DispatcherProvider
import com.github.deianvn.eazyplayer.utils.storage.SharedPrefStorage
import kotlinx.coroutines.withContext
import timber.log.Timber


private val CONFIG_KEY = "config"

class ConfigRepository(
    private val sharedPrefStorage: SharedPrefStorage,
    private val dispatcherProvider: DispatcherProvider
) {

    private var cachedConfig: Config? = null

    suspend fun loadConfig(): Config = withContext(dispatcherProvider.io) {
        cachedConfig ?: try {
            val result = sharedPrefStorage.getObject(
                key = CONFIG_KEY,
                javaType = ConfigDTO::class.java
            )

            val config = Config(
                mediaSourceLocations = result?.mediaSourceLocations ?: emptyList()
            )

            cachedConfig = config
            config
        } catch (e: Exception) {
            throw toStageError(e).also {
                Timber.e(e, "Could not load config data")
            }
        }
    }

    suspend fun saveConfig(config: Config) = withContext(dispatcherProvider.io) {
        try {
            sharedPrefStorage.putObject(
                key = CONFIG_KEY,
                value = ConfigDTO(
                    mediaSourceLocations = config.mediaSourceLocations
                ),
                javaType = ConfigDTO::class.java
            )

            cachedConfig = config
        } catch (e: Exception) {
            throw toStageError(e).also {
                Timber.e(e, "Could not save config")
            }
        }
    }

}
