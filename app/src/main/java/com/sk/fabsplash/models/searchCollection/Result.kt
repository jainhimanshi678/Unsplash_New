package com.sk.fabsplash.models.searchCollection

data class Result(
    val cover_photo: CoverPhoto,
    val description: String,
    val featured: Boolean,
    val id: String,
    val last_collected_at: String,
    val links: LinksXX,
    val `private`: Boolean,
    val published_at: String,
    val share_key: String,
    val title: String,
    val total_photos: Int,
    val updated_at: String,
    val user: UserX
)