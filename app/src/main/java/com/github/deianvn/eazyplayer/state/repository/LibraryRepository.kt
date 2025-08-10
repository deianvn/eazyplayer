package com.github.deianvn.eazyplayer.state.repository

import com.github.deianvn.compose.director.utils.toStageError
import com.github.deianvn.eazyplayer.state.dto.LibraryDTO
import com.github.deianvn.eazyplayer.state.model.Library
import com.github.deianvn.eazyplayer.utils.coroutines.DispatcherProvider
import com.github.deianvn.eazyplayer.utils.listAudioFiles
import com.github.deianvn.eazyplayer.utils.storage.SharedPrefStorage
import kotlinx.coroutines.withContext
import okio.Path
import okio.Path.Companion.toPath
import timber.log.Timber


private const val KEY_LIBRARY = "library"

class LibraryRepository(
    private val configRepository: ConfigRepository,
    private val sharedPrefStorage: SharedPrefStorage,
    private val dispatcherProvider: DispatcherProvider
) {

    private var cachedLibrary: Library? = null

    suspend fun updateLibrary(): Library = withContext(dispatcherProvider.io) {
        try {
            val mediaSourceLocations = configRepository.loadConfig().mediaSourceLocations

            val mediaFiles: List<Path> = mediaSourceLocations
                .map { it.toPath() }
                .flatMap { listAudioFiles(it) }

            val library = cachedLibrary?.copy(mediaFiles = mediaFiles)
                ?: Library(mediaFiles = mediaFiles)

            sharedPrefStorage.putObject(
                key = KEY_LIBRARY,
                value = LibraryDTO(mediaFiles = library.mediaFiles.map { it.toString() }),
                javaType = LibraryDTO::class.java
            )

            cachedLibrary = library

            library
        } catch (e: Exception) {
            throw toStageError(e).also {
                Timber.e(e, "Could not update library")
            }
        }
    }

    suspend fun getLibrary(): Library = withContext(dispatcherProvider.io) {
        cachedLibrary ?: try {
            val result = sharedPrefStorage.getObject(
                key = KEY_LIBRARY, javaType = LibraryDTO::class.java
            )

            val library = Library(
                mediaFiles = result?.mediaFiles?.map { it.toPath() } ?: emptyList()
            )

            cachedLibrary = library
            library
        } catch (e: Exception) {
            throw toStageError(e).also {
                Timber.e(e, "Could not get library")
            }
        }
    }

}
