package com.sk.fabsplash.repository

import com.sk.fabsplash.models.collection.CollectionResult
import com.sk.fabsplash.models.photo.PhotoResponse
import com.sk.fabsplash.models.searchCollection.SearchCollectionResponse
import com.sk.fabsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

interface IRemoteDataRepository {

    /**
     * Get photo
     */
    suspend fun getPhoto(count: Int): Response<PhotoResponse>

    /**
     * Get search photo response
     */
    suspend fun getSearchPhotoResponse(query: String, count: Int): Response<SearchPhotoResponse>

    /**
     * Get collections
     */
    suspend fun getCollection(count: Int): Response<CollectionResult>

    /**
     * Get search collections
     */
    suspend fun getSearchCollection(query: String, count: Int): Response<SearchCollectionResponse>?
}