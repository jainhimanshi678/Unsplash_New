package com.sk.fabsplash.models.searchPhoto

import java.io.Serializable

data class Links(
    val download: String,
    val html: String,
    val self: String
) : Serializable