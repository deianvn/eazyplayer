package com.github.deianvn.eazyplayer.state.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class LibraryDTO(
    val mediaFiles: List<String>? = null
)
