package com.sk.fabsplash.repository

import android.graphics.Bitmap
import com.sk.fabsplash.models.photo.PhotoResponseItem

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