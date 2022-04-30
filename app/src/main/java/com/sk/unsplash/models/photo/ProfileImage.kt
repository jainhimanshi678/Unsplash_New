package com.sk.unsplash.models.photo

import java.io.Serializable

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Serializable