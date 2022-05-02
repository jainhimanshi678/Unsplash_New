package com.sk.fabsplash.models.photo

import java.io.Serializable

data class LinksX(
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
) : Serializable