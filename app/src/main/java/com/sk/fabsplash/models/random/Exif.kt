package com.sk.fabsplash.models.random

import java.io.Serializable

data class Exif(
    val aperture: String,
    val exposure_time: String,
    val focal_length: String,
    val iso: Int,
    val make: String,
    val model: String
) : Serializable