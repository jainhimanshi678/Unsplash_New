package com.sk.unsplash.interfaces

import com.sk.unsplash.models.photo.PhotoResponseItem
import retrofit2.http.Url

interface IMainActivity {
    /***
     * Set long press listener for homefragment
     */
    fun setPhotoLongPressListener(photo: PhotoResponseItem)

    /**
     * Send photo url
     */
    fun sendLink(photo: String)
}