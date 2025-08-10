package com.github.deianvn.eazyplayer.state.model

import okio.Path


data class Library(
    val mediaFiles: List<Path> = emptyList()
)
