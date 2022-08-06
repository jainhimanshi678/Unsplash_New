package com.sk.fabsplash.models.searchCollection

data class SearchCollectionResponse(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)