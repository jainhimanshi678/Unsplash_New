package com.sk.unsplash.repository

import com.sk.unsplash.models.photo.PhotoResponse
import retrofit2.Response

interface IRemoteDataRepository {

    /**
     * Get photo
     */
    suspend fun getphoto(): Response<PhotoResponse>
}