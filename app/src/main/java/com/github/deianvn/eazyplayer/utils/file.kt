package com.github.deianvn.eazyplayer.utils

import okio.FileSystem
import okio.Path
import java.util.Locale

val SUPPORTED_AUDIO_FILE_EXTENSIONS = setOf("mp3", "ogg")

fun Path.extension(): String {
    val fileName = name
    val index = fileName.lastIndexOf('.')
    return if (index != -1 && index < fileName.length - 1) {
        fileName.substring(index + 1).lowercase(Locale.ROOT)
    } else {
        ""
    }
}

fun listAudioFiles(rootDir: Path): List<Path> {
    val fs = FileSystem.SYSTEM
    val result = mutableListOf<Path>()

    fun walk(dir: Path) {
        fs.list(dir).forEach { path ->
            when {
                fs.metadata(path).isDirectory && !path.name.startsWith(".")
                    -> walk(path)
                path.extension() in SUPPORTED_AUDIO_FILE_EXTENSIONS -> result.add(path)
            }
        }
    }

    walk(rootDir)
    return result
}
