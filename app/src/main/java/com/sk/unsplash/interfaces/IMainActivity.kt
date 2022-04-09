package com.sk.unsplash.interfaces

import com.sk.unsplash.models.photo.PhotoResponseItem

interface IMainActivity {
    /***
     * Set long press listener for homefragment
     */
    fun setPhotoLongPressListener(photo:PhotoResponseItem)
}