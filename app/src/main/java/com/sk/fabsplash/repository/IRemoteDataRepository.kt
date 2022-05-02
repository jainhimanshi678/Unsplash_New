package com.sk.fabsplash.repository

import com.sk.fabsplash.models.photo.PhotoResponse
import com.sk.fabsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

interface IRemoteDataRepository {

    /**
     * Get photo
     */
    suspend fun getPhoto(count:Int): Response<PhotoResponse>

    /**
     * Get search photo response
     */
    suspend fun getSearchPhotoResponse(query: String,count: Int): Response<SearchPhotoResponse>
}