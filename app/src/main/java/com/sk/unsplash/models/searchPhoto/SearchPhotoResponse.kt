package com.sk.unsplash.models.searchPhoto

import com.sk.unsplash.models.photo.PhotoResponseItem

data class SearchPhotoResponse(
    val results: List<PhotoResponseItem>,
    val total: Int,
    val total_pages: Int
)