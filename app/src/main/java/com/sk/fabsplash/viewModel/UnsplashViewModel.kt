package com.sk.fabsplash.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.fabsplash.models.photo.PhotoResponse
import com.sk.fabsplash.models.photo.PhotoResponseItem
import com.sk.fabsplash.models.searchPhoto.SearchPhotoResponse
import com.sk.fabsplash.repository.LocalDataRepository
import com.sk.fabsplash.repository.RemoteDataRepository
import kotlinx.coroutines.launch

class UnsplashViewModel : ViewModel() {

    private val remoteRepository = RemoteDataRepository

    private val localRepository = LocalDataRepository

    fun getPhotoResponse(count: Int, listener: (PhotoResponse?) -> Unit) = viewModelScope.launch {
        try {
            val response = remoteRepository.getPhoto(count)
            if (response.isSuccessful) {
                response.body()?.let {
                    listener(it)
                }
            } else {
                listener(null)
            }
        } catch (e: Exception) {
            listener(null)
        }
    }

    /**
     * To get searched photo
     */
    fun getSearchResponseForPhoto(
        query: String,
        count: Int,
        listener: (SearchPhotoResponse?) -> Unit
    ) =
        viewModelScope.launch {
            try {
                val response = remoteRepository.getSearchPhotoResponse(query, count)
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener(it)
                    }
                } else {
                    listener(null)
                }
            } catch (e: Exception) {
                listener(null)
            }
        }

    /**
     * To get user collection
     */
    fun getUserCollection(
        username: String,
        count: Int,
        listener: (SearchPhotoResponse?) -> Unit
    ) =
        viewModelScope.launch {
            try {
                val response = remoteRepository.getSearchPhotoResponse(username, count)
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener(it)
                    }
                } else {
                    listener(null)
                }
            } catch (e: Exception) {
                listener(null)
            }
        }

    /**
     *Saves the image in gallery.
     */
    fun saveImage(
        image: Bitmap,
        photoResponseItem: PhotoResponseItem?,
        listener: (Boolean) -> Unit
    ) = viewModelScope.launch {
        try {
            localRepository.savePhoto(image, photoResponseItem, listener)
        } catch (e: Exception) {

        }
    }

}