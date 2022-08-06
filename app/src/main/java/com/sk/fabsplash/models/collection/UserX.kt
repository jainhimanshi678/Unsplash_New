package com.sk.fabsplash.models.collection

data class UserX(
    val bio: String,
    val id: String,
    val links: LinksXXX,
    val location: String,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImageX,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val updated_at: String,
    val username: String
)