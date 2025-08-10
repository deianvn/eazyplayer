package com.github.deianvn.eazyplayer.state.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ConfigDTO(
    val mediaSourceLocations: List<String>? = null
)
