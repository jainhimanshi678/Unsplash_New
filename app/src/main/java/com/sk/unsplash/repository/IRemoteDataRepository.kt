package com.sk.unsplash.repository

import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
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