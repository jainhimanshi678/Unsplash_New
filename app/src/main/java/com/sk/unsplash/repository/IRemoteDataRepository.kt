package com.sk.unsplash.repository

import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

interface IRemoteDataRepository {

    /**
     * Get photo
     */
    suspend fun getphoto(): Response<PhotoResponse>

    /**
     * Get search photo response
     */
    suspend fun getSearchPhotoResponse(query: String): Response<SearchPhotoResponse>
}