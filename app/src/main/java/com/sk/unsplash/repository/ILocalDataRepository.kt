package com.sk.unsplash.repository

import android.graphics.Bitmap
import com.sk.unsplash.models.photo.PhotoResponseItem

interface ILocalDataRepository {

    /**
     * Get photo
     */
    suspend fun savePhoto(
        image: Bitmap,
        photoResponseItem: PhotoResponseItem?,
        listener: (Boolean) -> Unit
    )
}