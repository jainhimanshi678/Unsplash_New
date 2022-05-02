package com.sk.fabsplash.models.searchPhoto

import com.sk.fabsplash.models.photo.PhotoResponseItem

data class SearchPhotoResponse(
    val results: List<PhotoResponseItem>,
    val total: Int,
    val total_pages: Int
)