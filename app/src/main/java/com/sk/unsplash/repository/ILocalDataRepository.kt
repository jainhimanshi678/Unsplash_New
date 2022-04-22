package com.sk.unsplash.repository

import android.graphics.Bitmap
import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

interface ILocalDataRepository {

    /**
     * Get photo
     */
    suspend fun savePhoto(image: Bitmap, photoResponseItem: PhotoResponseItem?, listener: (Boolean) -> Unit)

    /**
     * Get search photo response
     */
    suspend fun getSearchPhotoResponse(query: String): Response<SearchPhotoResponse>
}