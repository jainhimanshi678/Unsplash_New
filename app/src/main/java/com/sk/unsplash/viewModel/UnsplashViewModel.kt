package com.sk.unsplash.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
import com.sk.unsplash.repository.LocalDataRepository
import com.sk.unsplash.repository.RemoteDataRepository
import kotlinx.coroutines.launch

class UnsplashViewModel : ViewModel() {

    private val remoteRepository = RemoteDataRepository

    private val localRepository = LocalDataRepository

    fun getPhotoResponse(listener: (PhotoResponse?) -> Unit) = viewModelScope.launch {
        try {
            val response = remoteRepository.getPhoto()
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
    fun getSearchResponseForPhoto(query: String, listener: (SearchPhotoResponse?) -> Unit) =
        viewModelScope.launch {
            try {
                val response = remoteRepository.getSearchPhotoResponse(query)
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
     *
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